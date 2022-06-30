package com.aqulasoft.fireman.tracker.components;

import com.aqulasoft.fireman.tracker.models.DictionaryLastPointDto;
import com.aqulasoft.fireman.tracker.models.VehiclePositionDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class DictionaryLastPoint {
    int maxNumberOfPositionInDictionary = 100;
    final private HashMap<String, List<VehiclePositionDto>> vehiclePositionDictionary = new HashMap<>();

    public DictionaryLastPointDto addToDictionary(String eventId, VehiclePositionDto vehiclePositionDto) {
        List<VehiclePositionDto> listOfVehiclePositions = vehiclePositionDictionary.get(eventId);
        if (listOfVehiclePositions == null) {
            List<VehiclePositionDto> list = new ArrayList<>();
            list.add(vehiclePositionDto);
            vehiclePositionDictionary.put(eventId, list);
            return new DictionaryLastPointDto(false, null);
        }

        listOfVehiclePositions.add(vehiclePositionDto);
        //vehiclePositionDictionary.put(eventId, listOfVehiclePositions);
        if (listOfVehiclePositions.size() == maxNumberOfPositionInDictionary) {
            return new DictionaryLastPointDto(true, listOfVehiclePositions);
        }
        return new DictionaryLastPointDto(false, null);
    }

}
