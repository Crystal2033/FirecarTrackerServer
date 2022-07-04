package com.aqulasoft.fireman.tracker.services.impl;

import com.aqulasoft.fireman.tracker.models.VehicleStatRequest;
import com.aqulasoft.fireman.tracker.services.VehicleStatService;

public class VehicleStatServiceImpl implements VehicleStatService {
    @Override
    public VehicleStatRequest addVehicle(VehicleStatRequest vehicleStatRequest) {
        return new VehicleStatRequest();
    }
}
