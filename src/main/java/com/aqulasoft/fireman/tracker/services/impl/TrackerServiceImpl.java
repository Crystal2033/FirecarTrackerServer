package com.aqulasoft.fireman.tracker.services.impl;

import com.aqulasoft.fireman.tracker.components.DictionaryLastPoint;
import com.aqulasoft.fireman.tracker.services.TrackerServices;
import org.modelmapper.ModelMapper;

public class TrackerServiceImpl implements TrackerServices {
    private final ModelMapper mapper;
    private final DictionaryLastPoint dictionary;

    public TrackerServiceImpl(ModelMapper mapper, DictionaryLastPoint dictionary) {
        this.mapper = mapper;
        this.dictionary = dictionary;
    }
}
