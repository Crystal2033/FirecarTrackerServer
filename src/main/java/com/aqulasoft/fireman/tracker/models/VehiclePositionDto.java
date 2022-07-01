package com.aqulasoft.fireman.tracker.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VehiclePositionDto {
    private String eventId;
    private String id;
    private LocalDateTime positionTime;
    private float longitude;
    private float latitude;
}
