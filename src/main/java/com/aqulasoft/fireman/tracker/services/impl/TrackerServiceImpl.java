package com.aqulasoft.fireman.tracker.services.impl;

import com.aqulasoft.fireman.tracker.components.VehiclePositionCache;
import com.aqulasoft.fireman.tracker.services.TrackerServices;
import org.modelmapper.ModelMapper;

public class TrackerServiceImpl implements TrackerServices {
    private final ModelMapper mapper;
    private final VehiclePositionCache dictionary;

    public TrackerServiceImpl(ModelMapper mapper, VehiclePositionCache dictionary) {
        this.mapper = mapper;
        this.dictionary = dictionary;
    }
}
