package com.aqulasoft.fireman.tracker.database.entities;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "PositionsBlock")
public class PositionsBlockEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid") // генерация id
    @Column(length = 32, updatable = false, nullable = false)
    private String id; // primary key in data base

    private int driverId;
    private int eventId;
    private String googleGeo;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
