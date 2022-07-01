package com.aqulasoft.fireman.tracker.controllers;

import com.aqulasoft.fireman.tracker.models.VehiclePositionsRequest;
import com.aqulasoft.fireman.tracker.services.TrackerServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tracker")
public class TrackerController {
    private final TrackerServices trackerServices;

    public TrackerController(TrackerServices trackerServices) {
        this.trackerServices = trackerServices;
    }

    @PostMapping("")
    public VehiclePositionsRequest addPositions(VehiclePositionsRequest vehiclePositionRequest) {
        return trackerServices.addPositions(vehiclePositionRequest);
    }
}
