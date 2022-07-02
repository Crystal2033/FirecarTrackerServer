package com.aqulasoft.fireman.tracker.database.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "VehiclePosition")
public class VehiclePositionEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid") // генерация id
    @Column(length = 32, updatable = false, nullable = false)
    private String id;

    private float latitude;
    private float longitude;
    private LocalDateTime positionTime;

    @ManyToOne
    @JoinColumn(name = "pos_block_id")
    private EventBlockEntity posBlock;



}
