package com.aqulasoft.fireman.tracker.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VehiclePositionDto {
    private String id;
    private String eventId;
    //private LocalDateTime positionTime;
    private float longitude;
    private float latitude;
}
