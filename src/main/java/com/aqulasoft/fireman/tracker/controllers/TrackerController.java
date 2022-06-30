package com.aqulasoft.fireman.tracker.controllers;

import com.aqulasoft.fireman.tracker.models.VehiclePositionDto;
import com.aqulasoft.fireman.tracker.models.VehiclePositionRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tracker")
public class TrackerController {
    @PostMapping("")
    public List<VehiclePositionDto> addPositions(List<VehiclePositionRequest> vehiclePositions)
    {
        return new ArrayList<>();
    }
}
