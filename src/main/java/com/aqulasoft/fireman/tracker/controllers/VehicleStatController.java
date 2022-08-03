package com.aqulasoft.fireman.tracker.controllers;

import com.aqulasoft.fireman.tracker.models.VehicleStatRequest;
import com.aqulasoft.fireman.tracker.services.VehicleStatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleStatController {
    private final VehicleStatService vehicleStatService;

    public VehicleStatController(VehicleStatService vehicleStatService) {
        this.vehicleStatService = vehicleStatService;
    }

    @PostMapping("")
    public VehicleStatRequest addVehicle(@RequestBody VehicleStatRequest vehicleStatRequest) {
        return vehicleStatService.addVehicle(vehicleStatRequest);
    }

    @GetMapping("")
    public String foo()
    {
        System.out.println("Aaaa");
        return "aaa";
    }
}
