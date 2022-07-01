package com.aqulasoft.fireman.tracker.services;

import com.aqulasoft.fireman.tracker.exceptions.EmptyArrayException;
import com.aqulasoft.fireman.tracker.exceptions.EmptyVehicleException;
import com.aqulasoft.fireman.tracker.models.VehiclePositionDto;
import com.aqulasoft.fireman.tracker.models.VehiclePositionsRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface TrackerServices {
    public VehiclePositionsRequest addPositions (VehiclePositionsRequest posReq) throws EmptyArrayException, EmptyVehicleException;

}
