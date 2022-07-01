package com.aqulasoft.fireman.tracker.components;


import com.aqulasoft.fireman.tracker.models.VehiclePositionDto;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class VehiclePositionCache {
    private final HashMap<String, ArrayList<VehiclePositionDto>> dictionary = new HashMap<>();
    private static final Integer MAX_SIZE = 100;

    public Pair<Boolean, ArrayList<VehiclePositionDto>> addPoint(String eventId, VehiclePositionDto positionDto) {
        ArrayList<VehiclePositionDto> arrPoints;

        if((arrPoints = dictionary.get(eventId)) == null){ //creating list
            arrPoints = new ArrayList<>();
            dictionary.put(eventId, arrPoints);
        }

        else if(arrPoints.size() >= MAX_SIZE){
            ArrayList<VehiclePositionDto> packPoints = new ArrayList<>(arrPoints);
            arrPoints.clear();
            arrPoints.add(positionDto);
            return Pair.of(true, packPoints);
        }

        arrPoints.add(positionDto);
        return Pair.of(false, arrPoints);
    }

    public ArrayList<VehiclePositionDto> loadPackPoints(String eventId)
    {
        if(!dictionary.containsKey(eventId))
        {
            return null;
        }
        return new ArrayList<>(dictionary.get(eventId));
    }

}