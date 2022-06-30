package com.aqulasoft.fireman.tracker.components;

import com.aqulasoft.fireman.tracker.models.BoolPair;
import com.aqulasoft.fireman.tracker.models.VehiclePositionDto;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class DictionaryLastPoint {
    private final HashMap<String, ArrayList<VehiclePositionDto>> dictionary = new HashMap<>();
    static final Integer maxSize = 100;

    private BoolPair<ArrayList<VehiclePositionDto>> addPoint(String eventId, VehiclePositionDto positionDto) {
        ArrayList<VehiclePositionDto> arrPoints;

        if((arrPoints = dictionary.get(eventId)) == null){ //creating list
            arrPoints = new ArrayList<>();
            arrPoints.add(positionDto);
            dictionary.put(eventId, arrPoints);
            return new BoolPair<>(false, arrPoints);
        }

        if(arrPoints.size() > maxSize){ // checking
            ArrayList<VehiclePositionDto> packPoints = new ArrayList<>(arrPoints);
            arrPoints.clear();
            arrPoints.add(packPoints.get(packPoints.size() - 1));
            return new BoolPair<>(true, packPoints);
        }

        arrPoints.add(positionDto);
        return new BoolPair<>(false, arrPoints);
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
