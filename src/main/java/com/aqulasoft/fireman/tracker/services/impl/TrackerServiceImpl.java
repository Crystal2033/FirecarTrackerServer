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
    public VehiclePositionsRequest addPositions(VehiclePositionsRequest vehiclePositionsRequest) throws EmptyArrayException, EmptyVehicleException, EmptyEventBlockException {

        if(vehiclePositionsRequest.getPositions().isEmpty())
        {
            throw new EmptyArrayException();
        }

        List<VehiclePositionDto> vehiclePositions = vehiclePositionsRequest.getPositions();
        VehiclePositionDto lastPoint = dictionary.getLastPoint(vehiclePositionsRequest.getVehicleId());

        // проверка для первого элемента
        if (lastPoint == null) {
            // нет машины в словаре (с прошлых запросов). Создаем блок. То есть это начало работы машины. Создается голова списка
            EventBlockEntity newEventBlockEntity = new EventBlockEntity();

            Optional<VehicleStatEntity> vehicleOptional = vehicleStatRepository.findOptionalById(vehiclePositionsRequest.getVehicleId());
            VehicleStatEntity vehicle = vehicleOptional.orElseThrow(EmptyVehicleException::new);
            newEventBlockEntity.setVehicleId(vehicle);

            eventBlockRepository.save(newEventBlockEntity);

        } else if (!Objects.equals(lastPoint.getEventId(), vehiclePositions.get(0).getEventId())) {
            // послдений элемент предыдущего запроса имел другое событие, нужно закрыть прошлый блок и создать новый
            // Два соседних переданных массива имеют точки от разных блоков.
            EventBlockEntity newEventBlockEntity = new EventBlockEntity();

            Optional<VehicleStatEntity> vehicleOptional = vehicleStatRepository.findOptionalById(vehiclePositionsRequest.getVehicleId());
            VehicleStatEntity vehicle = vehicleOptional.orElseThrow(EmptyVehicleException::new);
            newEventBlockEntity.setVehicleId(vehicle);// установка автомобиля в бд

            // соединяем
            Optional<EventBlockEntity> previousEventBlockOptional = eventBlockRepository.findOptionalById(lastPoint.getEventId());
            EventBlockEntity previousEventBlockEntity = previousEventBlockOptional.orElseThrow(EmptyEventBlockException::new);

            previousEventBlockEntity.setNextPosBlockId(newEventBlockEntity);
            eventBlockRepository.save(newEventBlockEntity);
        }


        for (int i = 0; i < vehiclePositions.size() - 1; i++) {
            //TODO: продумать, как не вызывать постоянно eventBlockRepository для получения currentEventBlockEntity
            Optional<EventBlockEntity> currentEventBlockOptional
                    = eventBlockRepository.findOptionalById(vehiclePositions.get(i).getEventId());
            EventBlockEntity currentEventBlockEntity = currentEventBlockOptional.orElseThrow(EmptyEventBlockException::new);

            VehiclePositionEntity currentPosition = mapper.map(vehiclePositions.get(i), VehiclePositionEntity.class);

            currentPosition.setPosBlock(currentEventBlockEntity);

            List<VehiclePositionEntity> positionEntities = currentEventBlockEntity.getPositions();
            positionEntities.add(currentPosition);
            currentEventBlockEntity.setPositions(positionEntities);
            eventBlockRepository.save(currentEventBlockEntity);

            vehiclePositionRepository.save(currentPosition);

            if (!Objects.equals(vehiclePositions.get(i).getEventId(), vehiclePositions.get(i + 1).getEventId())) {
                // мы закрываем левый блок и создаем правый

                //Создаем блок
                EventBlockEntity newEventBlockEntity = new EventBlockEntity();

                Optional<VehicleStatEntity> vehicleOptional = vehicleStatRepository.findOptionalById(vehiclePositionsRequest.getVehicleId());
                VehicleStatEntity vehicle = vehicleOptional.orElseThrow(EmptyVehicleException::new);
                newEventBlockEntity.setVehicleId(vehicle);

                // соединяем
                Optional<EventBlockEntity> previousEventBlockOptional
                        = eventBlockRepository.findOptionalById(vehiclePositions.get(i).getEventId());
                EventBlockEntity previousEventBlockEntity = previousEventBlockOptional.orElseThrow(EmptyEventBlockException::new);
                previousEventBlockEntity.setNextPosBlockId(newEventBlockEntity);
                eventBlockRepository.save(newEventBlockEntity);
            }
        }

        Optional<EventBlockEntity> currentEventBlockOptional
                = eventBlockRepository.findOptionalById(vehiclePositions.get(vehiclePositions.size() - 1).getEventId());
        EventBlockEntity currentEventBlockEntity = currentEventBlockOptional.orElseThrow(EmptyEventBlockException::new);

        VehiclePositionEntity currentPosition = mapper.map(vehiclePositions.get(vehiclePositions.size() - 1), VehiclePositionEntity.class);

        currentPosition.setPosBlock(currentEventBlockEntity);

        List<VehiclePositionEntity> positionEntities = currentEventBlockEntity.getPositions();
        positionEntities.add(currentPosition);
        currentEventBlockEntity.setPositions(positionEntities);
        eventBlockRepository.save(currentEventBlockEntity);

        vehiclePositionRepository.save(currentPosition);

        dictionary.addLastPoint(
                vehiclePositionsRequest.getVehicleId(),
                vehiclePositions.get(vehiclePositions.size() - 1)
        );

        return vehiclePositionsRequest;
    }
}
