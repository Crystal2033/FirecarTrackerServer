package com.aqulasoft.fireman.tracker.services;

import com.aqulasoft.fireman.tracker.exceptions.EmptyArrayException;
import com.aqulasoft.fireman.tracker.exceptions.EmptyVehicleException;
import com.aqulasoft.fireman.tracker.models.VehiclePositionsRequest;
import org.springframework.stereotype.Service;


@Service
public interface TrackerServices {
    VehiclePositionsRequest addPositions(VehiclePositionsRequest vehiclePositionsRequest) throws EmptyArrayException,
            EmptyVehicleException;

}
