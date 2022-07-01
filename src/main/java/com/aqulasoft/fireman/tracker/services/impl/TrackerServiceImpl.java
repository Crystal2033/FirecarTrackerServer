package com.aqulasoft.fireman.tracker.services.impl;

import com.aqulasoft.fireman.tracker.components.VehiclePositionCache;
import com.aqulasoft.fireman.tracker.database.entities.EventBlockEntity;
import com.aqulasoft.fireman.tracker.database.entities.VehiclePositionEntity;
import com.aqulasoft.fireman.tracker.database.entities.VehicleStatEntity;
import com.aqulasoft.fireman.tracker.database.repositories.EventBlockRepository;
import com.aqulasoft.fireman.tracker.database.repositories.VehiclePositionRepository;
import com.aqulasoft.fireman.tracker.database.repositories.VehicleStatRepository;
import com.aqulasoft.fireman.tracker.exceptions.EmptyArrayException;
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
    public VehiclePositionsRequest addPositions(VehiclePositionsRequest vehiclePositionsRequest) throws EmptyArrayException, EmptyVehicleException {

        List<VehiclePositionDto> vehiclePositions = vehiclePositionsRequest.getPositions();
        VehiclePositionDto lastPoint = dictionary.getLastPoint(vehiclePositionsRequest.getVehicleId());

        // проверка для первого элемента
        if (lastPoint == null) {
            // нет машины в словаре (с прошлых запросов). Создаем блок
            EventBlockEntity newEventBlockEntity = new EventBlockEntity();

            Optional<VehicleStatEntity> vehicle = vehicleStatRepository.findOptionalById(vehiclePositionsRequest.getVehicleId());
            if (vehicle.isEmpty()) {
                throw new EmptyArrayException();
            }
            newEventBlockEntity.setVehicleId(vehicle.get());
        } else if (!Objects.equals(lastPoint.getEventId(), vehiclePositions.get(0).getEventId())) {
            // послдений элемнет предыдущего запроса имел другое событие, нужно закрыть прошлый блок и создать новый

            //Создаем блок
            EventBlockEntity newEventBlockEntity = new EventBlockEntity();

            Optional<VehicleStatEntity> vehicle = vehicleStatRepository.findOptionalById(vehiclePositionsRequest.getVehicleId());
            if (vehicle.isEmpty()) {
                throw new EmptyArrayException();
            }
            newEventBlockEntity.setVehicleId(vehicle.get());

            // соединяем
            Optional<EventBlockEntity> previousEventBlockEntity = eventBlockRepository.findOptionalById(lastPoint.getEventId());
            if (previousEventBlockEntity.isEmpty()) {
                throw new EmptyArrayException();
            }
            (previousEventBlockEntity.get()).setNextPosBlockId(newEventBlockEntity);
        }

        // проверка от первого до предпоследнего
        List<VehiclePositionEntity> vehiclePositionEntities = new ArrayList<>();
        for (int i = 0; i < vehiclePositions.size()-1; i++) {
            if (vehiclePositions.get(i).getEventId() != vehiclePositions.get(i+1).getEventId()) {
                // мы закрываем левый блок и создаем правый

                //Создаем блок
                EventBlockEntity newEventBlockEntity = new EventBlockEntity();

                Optional<VehicleStatEntity> vehicle = vehicleStatRepository.findOptionalById(vehiclePositionsRequest.getVehicleId());
                if (vehicle.isEmpty()) {
                    throw new EmptyArrayException();
                }
                newEventBlockEntity.setVehicleId(vehicle.get());

                // соединяем
                Optional<EventBlockEntity> previousEventBlockEntity
                        = eventBlockRepository.findOptionalById(vehiclePositions.get(i).getEventId());
                if (previousEventBlockEntity.isEmpty()) {
                    throw new EmptyArrayException();
                }
                previousEventBlockEntity.get().setNextPosBlockId(newEventBlockEntity);

                // нужно добавить текующий элемент! Закинуть точку в список предыдущего блока
                VehiclePositionEntity currentVehiclePositionEntity
                List<VehiclePositionEntity> previousVehiclePositionEntities = previousEventBlockEntity.get().getPositions();
                previousVehiclePositionEntities.add(mapper.map())



            } else {
                // просто добавляем в блок

            }
        }


        dictionary.addLastPoint(
                vehiclePositionsRequest.getVehicleId(),
                vehiclePositions.get(vehiclePositions.size()-1)
        );

        return vehiclePositionsRequest;
    }
}
