package com.aqulasoft.fireman.tracker.controllers;

import com.aqulasoft.fireman.tracker.models.EventBlockRequest;
import com.aqulasoft.fireman.tracker.models.VehiclePositionsRequest;
import com.aqulasoft.fireman.tracker.services.EventBlockService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event")
public class EventBlockController {
    private final EventBlockService eventBlockService;

    public EventBlockController(EventBlockService eventBlockService) {
        this.eventBlockService = eventBlockService;
    }


    @PostMapping("")
    public EventBlockRequest addEventBlock(@RequestBody EventBlockRequest eventBlockRequest) {
        return eventBlockService.addEventBlock(eventBlockRequest);
    }


}
