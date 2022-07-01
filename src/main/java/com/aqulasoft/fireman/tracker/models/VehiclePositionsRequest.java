package com.aqulasoft.fireman.tracker.models;

import lombok.Data;

import java.util.List;

@Data
public class VehiclePositionsRequest {
    private String vehicleId;
    private List<VehiclePositionDto> positions;
}
