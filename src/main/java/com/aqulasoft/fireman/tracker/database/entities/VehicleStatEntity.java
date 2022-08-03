package com.aqulasoft.fireman.tracker.database.entities;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "VehicleStat")
public class VehicleStatEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid") // генерация id
    @Column(length = 32, updatable = false, nullable = false)
    private String id;

    @OneToMany (mappedBy = "vehicleId")
    //@JoinColumn (name="last_event_block_id")
    private List<EventBlockEntity> eventBlocks;

    @OneToOne
    @JoinColumn(name="last_event_block_id")
    private EventBlockEntity lastEventBlockId;

    private String vehicleId;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP") // генерация даты
    private LocalDateTime createTime;
    private LocalDateTime lastChangeTime;

}


