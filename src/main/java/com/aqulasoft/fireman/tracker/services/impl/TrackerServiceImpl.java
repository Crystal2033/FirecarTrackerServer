package com.aqulasoft.fireman.tracker.services.impl;

import com.aqulasoft.fireman.tracker.components.VehiclePositionCache;
import com.aqulasoft.fireman.tracker.database.entities.EventBlockEntity;
import com.aqulasoft.fireman.tracker.database.entities.VehiclePositionEntity;
import com.aqulasoft.fireman.tracker.database.entities.VehicleStatEntity;
import com.aqulasoft.fireman.tracker.database.repositories.EventBlockRepository;
import com.aqulasoft.fireman.tracker.database.repositories.VehicleStatRepository;
import com.aqulasoft.fireman.tracker.exceptions.EmptyArrayException;
import com.aqulasoft.fireman.tracker.exceptions.EmptyVehicleException;
import com.aqulasoft.fireman.tracker.models.VehiclePositionDto;
import com.aqulasoft.fireman.tracker.models.VehiclePositionsRequest;
import com.aqulasoft.fireman.tracker.services.TrackerServices;
import org.modelmapper.ModelMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TrackerServiceImpl implements TrackerServices {
    private final ModelMapper mapper;
    private final VehiclePositionCache positionCache;
    private final EventBlockRepository eventBlockRepository;
    private final VehicleStatRepository vehicleStatRepository;

    public TrackerServiceImpl(ModelMapper mapper, VehiclePositionCache positionCache, EventBlockRepository eventBlockRepository, VehicleStatRepository vehicleStatRepository) {
        this.mapper = mapper;
        this.positionCache = positionCache;
        this.eventBlockRepository = eventBlockRepository;
        this.vehicleStatRepository = vehicleStatRepository;
    }

    @Override
    public VehiclePositionsRequest addPositions(VehiclePositionsRequest vehiclePositionsRequest) throws EmptyArrayException, EmptyVehicleException {

        if (vehiclePositionsRequest.getPositions().isEmpty()) {
            throw new EmptyArrayException();
        }

        List<VehiclePositionDto> vehiclePositions = vehiclePositionsRequest.getPositions();

        Optional<VehicleStatEntity> vehicleStatOptional = vehicleStatRepository.findOptionalByVehicleId(vehiclePositionsRequest.getVehicleId());
        VehicleStatEntity vehicle = vehicleStatOptional.orElseThrow(EmptyVehicleException::new);

        EventBlockEntity currentEventBlockEntity = vehicle.getLastEventBlock();

        List<VehiclePositionEntity> newPositionEntities = new ArrayList<>();

        for (VehiclePositionDto positionDto : vehiclePositions) {
            if (currentEventBlockEntity == null) {
                currentEventBlockEntity = createNewBlock(positionDto.getEventId(), vehicle, null);
            }

            if (!Objects.equals(currentEventBlockEntity.getEventId(), positionDto.getEventId())) {
                if (!newPositionEntities.isEmpty()) {
                    currentEventBlockEntity.getPositions().addAll(newPositionEntities);
                    eventBlockRepository.save(currentEventBlockEntity);
                    newPositionEntities.clear();
                }
                currentEventBlockEntity = createNewBlock(positionDto.getEventId(), vehicle, currentEventBlockEntity);
            }
            VehiclePositionEntity newPositionEntity = mapper.map(positionDto, VehiclePositionEntity.class);
            newPositionEntity.setPosBlock(currentEventBlockEntity);
            newPositionEntities.add(newPositionEntity);
        }
        if (!newPositionEntities.isEmpty()) {
            currentEventBlockEntity.getPositions().addAll(newPositionEntities);
            eventBlockRepository.save(currentEventBlockEntity);
        }

        positionCache.addLastPoint(vehicle.getVehicleId(), vehiclePositions.get(vehiclePositions.size() - 1));
        return vehiclePositionsRequest;
    }

    private EventBlockEntity createNewBlock(String eventId, VehicleStatEntity vehicleStat, @Nullable EventBlockEntity lastBlock) {
        EventBlockEntity newEventBlockEntity = new EventBlockEntity();
        newEventBlockEntity.setVehicleStat(vehicleStat);
        newEventBlockEntity.setEventId(eventId);
        newEventBlockEntity.setPositions(new ArrayList<>());

        if (lastBlock != null) {
            lastBlock.setEndTime(LocalDateTime.now());
            newEventBlockEntity.setPrevPosBlock(lastBlock);
        }
        vehicleStat.setLastEventBlock(newEventBlockEntity);
        eventBlockRepository.save(newEventBlockEntity);
        return newEventBlockEntity;
    }
}
