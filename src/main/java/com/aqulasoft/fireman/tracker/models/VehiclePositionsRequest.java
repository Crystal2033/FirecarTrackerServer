package com.aqulasoft.fireman.tracker.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class VehiclePositionsRequest {
    private String vehicleId;
    private ArrayList<VehiclePositionDto> positions;
}
