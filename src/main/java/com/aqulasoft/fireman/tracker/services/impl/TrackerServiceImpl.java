package com.aqulasoft.fireman.tracker.services.impl;

import com.aqulasoft.fireman.tracker.components.VehiclePositionCache;
import com.aqulasoft.fireman.tracker.exceptions.EmptyArrayException;
import com.aqulasoft.fireman.tracker.models.VehiclePositionDto;
import com.aqulasoft.fireman.tracker.models.VehiclePositionsRequest;
import com.aqulasoft.fireman.tracker.services.TrackerServices;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrackerServiceImpl implements TrackerServices {
    private final ModelMapper mapper;
    private final VehiclePositionCache dictionary;

    public TrackerServiceImpl(ModelMapper mapper, VehiclePositionCache dictionary) {
        this.mapper = mapper;
        this.dictionary = dictionary;
    }

    @Override
    public VehiclePositionsRequest addPositions(VehiclePositionsRequest positionsRequest) throws EmptyArrayException {
        if(positionsRequest.getPositions().isEmpty())
        {
            throw new EmptyArrayException();
        }
        List<VehiclePositionDto> vehiclePositions = positionsRequest.getPositions();
        VehiclePositionDto lastVehiclePosition = vehiclePositions.get(vehiclePositions.size() - 1);
        dictionary.addLastPoint(lastVehiclePosition.getEventId() ,lastVehiclePosition);


        VehiclePositionDto previousPosition = null;
        //1.
        for(VehiclePositionDto position: vehiclePositions){
            if (previousPosition == null)
            {
                previousPosition = position;
                continue;
            }


            previousPosition = position;
        }
        return positionsRequest;
    }
}
