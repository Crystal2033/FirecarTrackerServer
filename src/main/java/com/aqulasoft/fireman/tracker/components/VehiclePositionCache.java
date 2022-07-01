package com.aqulasoft.fireman.tracker.components;


import com.aqulasoft.fireman.tracker.models.VehiclePositionDto;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class VehiclePositionCache {
    private final HashMap<String, VehiclePositionDto> dictionary = new HashMap<>();

    public void addLastPoint(String vehicleId, VehiclePositionDto positionDto) {
        dictionary.put(vehicleId, positionDto);
    }

    public VehiclePositionDto getLastPoint(String vehicleId) {
        return dictionary.get(vehicleId);
    }

}