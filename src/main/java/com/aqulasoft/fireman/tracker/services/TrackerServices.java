package com.aqulasoft.fireman.tracker.services;

import com.aqulasoft.fireman.tracker.models.VehiclePositionDto;
import com.aqulasoft.fireman.tracker.models.VehiclePositionsRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface TrackerServices {

    //post
    VehiclePositionsRequest addPositions(VehiclePositionsRequest vehiclePositionsRequest);
}
