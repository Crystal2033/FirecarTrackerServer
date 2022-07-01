package com.aqulasoft.fireman.tracker.services.impl;

import com.aqulasoft.fireman.tracker.components.VehiclePositionCache;
import com.aqulasoft.fireman.tracker.controllers.TrackerController;
import com.aqulasoft.fireman.tracker.models.VehiclePositionsRequest;
import com.aqulasoft.fireman.tracker.services.TrackerServices;
import org.modelmapper.ModelMapper;

public class TrackerServiceImpl implements TrackerServices {
    private final ModelMapper mapper;
    private final VehiclePositionCache dictionary;
    private final TrackerController trackerController;


    public TrackerServiceImpl(ModelMapper mapper, VehiclePositionCache dictionary, TrackerController trackerController) {
        this.mapper = mapper;
        this.dictionary = dictionary;
        this.trackerController = trackerController;
    }

    @Override
    public VehiclePositionsRequest addPositions(VehiclePositionsRequest vehiclePositionsRequest) {
        return trackerController.addPositions(vehiclePositionsRequest);
    }
}
