package com.aqulasoft.fireman.tracker.services.impl;

import com.aqulasoft.fireman.tracker.database.entities.VehicleStatEntity;
import com.aqulasoft.fireman.tracker.database.repositories.VehicleStatRepository;
import com.aqulasoft.fireman.tracker.models.VehicleStatRequest;
import com.aqulasoft.fireman.tracker.services.VehicleStatService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class VehicleStatServiceImpl implements VehicleStatService {

    private final VehicleStatRepository vehicleStatRepository;
    private final ModelMapper mapper;

    public VehicleStatServiceImpl(VehicleStatRepository vehicleStatRepository, ModelMapper mapper) {
        this.vehicleStatRepository = vehicleStatRepository;
        this.mapper = mapper;
    }

    @Override
    public VehicleStatRequest addVehicle(VehicleStatRequest vehicleStatRequest) {
        if (vehicleStatRequest.getVehicleId() != null) {
            //VehicleStatEntity vehicleStatEntity = new VehicleStatEntity();
            VehicleStatEntity vehicleStatEntity = mapper.map(vehicleStatRequest, VehicleStatEntity.class);
            vehicleStatEntity.setVehicleId(vehicleStatRequest.getVehicleId());
            vehicleStatEntity.setPosBlockHead(null);
            vehicleStatRepository.save(vehicleStatEntity);
        }
        return vehicleStatRequest;
    }
}
