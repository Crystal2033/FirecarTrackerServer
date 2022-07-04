package com.aqulasoft.fireman.tracker.services;

import com.aqulasoft.fireman.tracker.models.VehicleStatRequest;
import org.springframework.stereotype.Service;

@Service
public interface VehicleStatService {
    VehicleStatRequest addVehicle(VehicleStatRequest vehicleStatRequest);
}
