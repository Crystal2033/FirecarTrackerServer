package com.aqulasoft.fireman.tracker.services.impl;

import com.aqulasoft.fireman.tracker.components.VehiclePositionCache;
import com.aqulasoft.fireman.tracker.database.entities.EventBlockEntity;
import com.aqulasoft.fireman.tracker.database.entities.VehiclePositionEntity;
import com.aqulasoft.fireman.tracker.database.entities.VehicleStatEntity;
import com.aqulasoft.fireman.tracker.database.repositories.EventBlockRepository;
import com.aqulasoft.fireman.tracker.database.repositories.VehiclePositionRepository;
import com.aqulasoft.fireman.tracker.database.repositories.VehicleStatRepository;
import com.aqulasoft.fireman.tracker.exceptions.EmptyArrayException;
import com.aqulasoft.fireman.tracker.exceptions.EmptyEventBlockException;
import com.aqulasoft.fireman.tracker.exceptions.EmptyVehicleException;
import com.aqulasoft.fireman.tracker.models.VehiclePositionDto;
import com.aqulasoft.fireman.tracker.models.VehiclePositionsRequest;
import com.aqulasoft.fireman.tracker.services.TrackerServices;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TrackerServiceImpl implements TrackerServices {
    private final ModelMapper mapper;
    private final VehiclePositionCache dictionary;
    private final VehiclePositionRepository vehiclePositionRepository;
    private final EventBlockRepository eventBlockRepository;
    private final VehicleStatRepository vehicleStatRepository;

    public TrackerServiceImpl(ModelMapper mapper, VehiclePositionCache dictionary, VehiclePositionRepository vehiclePositionRepository, EventBlockRepository eventBlockRepository, VehicleStatRepository vehicleStatRepository) {
        this.mapper = mapper;
        this.dictionary = dictionary;
        this.vehiclePositionRepository = vehiclePositionRepository;
        this.eventBlockRepository = eventBlockRepository;
        this.vehicleStatRepository = vehicleStatRepository;
    }

    @Override
    public VehiclePositionsRequest addPositions(VehiclePositionsRequest vehiclePositionsRequest)
            throws EmptyArrayException, EmptyVehicleException, EmptyEventBlockException {

        if (vehiclePositionsRequest.getPositions().isEmpty()) {
            throw new EmptyArrayException();
        }

        List<VehiclePositionDto> vehiclePositions = vehiclePositionsRequest.getPositions();
        VehiclePositionDto lastPointFromCache = dictionary.getLastPoint(vehiclePositionsRequest.getVehicleId());

        Optional<VehicleStatEntity> vehicleOptional = vehicleStatRepository.findOptionalById(vehiclePositionsRequest.getVehicleId());
        VehicleStatEntity vehicle = vehicleOptional.orElseThrow(EmptyVehicleException::new);
        // проверка для первого элемента
        if (lastPointFromCache == null) {
            // нет машины в словаре (с прошлых запросов). Создаем блок. То есть это начало работы машины. Создается голова списка
            EventBlockEntity newEventBlockEntity = new EventBlockEntity();

            newEventBlockEntity.setVehicleId(vehicle);

            eventBlockRepository.save(newEventBlockEntity);

        } else if (!Objects.equals(lastPointFromCache.getEventId(), vehiclePositions.get(0).getEventId())) {
            // последний элемент предыдущего запроса имел другое событие, нужно закрыть прошлый блок и создать новый
            // Два соседних переданных массива имеют точки от разных блоков.
            EventBlockEntity newEventBlockEntity = new EventBlockEntity();
            newEventBlockEntity.setVehicleId(vehicle);// установка автомобиля в бд

            Optional<EventBlockEntity> previousEventBlockOptional = eventBlockRepository.findOptionalById(lastPointFromCache.getEventId());
            EventBlockEntity previousEventBlockEntity = previousEventBlockOptional.orElseThrow(EmptyEventBlockException::new);

            previousEventBlockEntity.setNextPosBlockId(newEventBlockEntity);
            eventBlockRepository.save(newEventBlockEntity);
        }


        Optional<EventBlockEntity> currentEventBlockOptional
                = eventBlockRepository.findOptionalById(vehiclePositions.get(0).getEventId());
        EventBlockEntity currentEventBlockEntity = currentEventBlockOptional.orElseThrow(EmptyEventBlockException::new);

        List<VehiclePositionEntity> newPositionEntities = new ArrayList<>();
        for (int i = 0; i < vehiclePositions.size() - 1; i++) {
            VehiclePositionEntity newPositionEntity = mapper.map(vehiclePositions.get(i), VehiclePositionEntity.class);
            newPositionEntity.setPosBlock(currentEventBlockEntity);

            newPositionEntities.add(newPositionEntity);

            vehiclePositionRepository.save(newPositionEntity); // may be don`t need

            if (!Objects.equals(vehiclePositions.get(i).getEventId(), vehiclePositions.get(i + 1).getEventId())) {
                // мы закрываем левый блок и создаем правый

                //Создаем блок
                EventBlockEntity newEventBlockEntity = new EventBlockEntity();
                newEventBlockEntity.setVehicleId(vehicle);
                currentEventBlockEntity.setNextPosBlockId(newEventBlockEntity);

                List<VehiclePositionEntity> allPositionsFromBlock = currentEventBlockEntity.getPositions();
                allPositionsFromBlock.addAll(newPositionEntities);

                currentEventBlockEntity.setPositions(allPositionsFromBlock);
                eventBlockRepository.save(currentEventBlockEntity);
                newPositionEntities.clear();

                currentEventBlockEntity = newEventBlockEntity;
                eventBlockRepository.save(newEventBlockEntity);
            }
        }
        //Adding last element which was not added in the loop
        VehiclePositionEntity newPositionEntity = mapper.map(vehiclePositions.get(vehiclePositions.size() - 1), VehiclePositionEntity.class);
        newPositionEntity.setPosBlock(currentEventBlockEntity);
        newPositionEntities.add(newPositionEntity);

        List<VehiclePositionEntity> allPositionsFromBlock = currentEventBlockEntity.getPositions();
        allPositionsFromBlock.addAll(newPositionEntities);

        currentEventBlockEntity.setPositions(allPositionsFromBlock);
        eventBlockRepository.save(currentEventBlockEntity);

        vehiclePositionRepository.save(newPositionEntity);

        dictionary.addLastPoint(
                vehiclePositionsRequest.getVehicleId(),
                vehiclePositions.get(vehiclePositions.size() - 1)
        );

        return vehiclePositionsRequest;
    }
}
