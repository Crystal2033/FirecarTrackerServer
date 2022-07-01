package com.aqulasoft.fireman.tracker.components;


import com.aqulasoft.fireman.tracker.models.VehiclePositionDto;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class VehiclePositionCache {
    private final HashMap<String, VehiclePositionDto> dictionary = new HashMap<>();

    public void addLastPoint(String eventId, VehiclePositionDto positionDto) {
        dictionary.put(eventId, positionDto);
    }

    public VehiclePositionDto getLastPoint(String eventId) {
        return dictionary.get(eventId);
    }

}