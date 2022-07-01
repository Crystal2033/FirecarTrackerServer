package com.aqulasoft.fireman.tracker.services;

import com.aqulasoft.fireman.tracker.models.VehiclePositionDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface TrackerServices {
    public void addPositions (ArrayList<VehiclePositionDto> positions);

}
