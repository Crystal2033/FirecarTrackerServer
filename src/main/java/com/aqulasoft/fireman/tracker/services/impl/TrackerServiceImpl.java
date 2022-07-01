package com.aqulasoft.fireman.tracker.services.impl;

import com.aqulasoft.fireman.tracker.components.VehiclePositionCache;
import com.aqulasoft.fireman.tracker.controllers.TrackerController;
import com.aqulasoft.fireman.tracker.models.VehiclePositionsRequest;
import com.aqulasoft.fireman.tracker.services.TrackerServices;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TrackerServiceImpl implements TrackerServices {
    private final ModelMapper mapper;
    private final VehiclePositionCache dictionary;

    public TrackerServiceImpl(ModelMapper mapper, VehiclePositionCache dictionary) {
        this.mapper = mapper;
        this.dictionary = dictionary;
    }

    @Override
    public VehiclePositionsRequest addPositions(VehiclePositionsRequest vehiclePositionsRequest) {
        return vehiclePositionsRequest;
    }
}
