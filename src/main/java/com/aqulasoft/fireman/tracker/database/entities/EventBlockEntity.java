package com.aqulasoft.fireman.tracker.database.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "EventBlock")
public class EventBlockEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32, updatable = false, nullable = false)
    private String id;

    @OneToOne // depends on itself
    @JoinColumn(name = "next_pos_block_id")
    private EventBlockEntity prevPosBlock;

    @ManyToOne
    @JoinColumn(name = "vehicle_stat_id")
    private VehicleStatEntity vehicleStat;

    @OneToOne(mappedBy = "lastEventBlock")
    @JoinColumn(name = "link_vehicle_id")
    private VehicleStatEntity linkVehicle; //don`t using

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime startTime;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "posBlock")
    private List<VehiclePositionEntity> positions;

    private String driverId;
    private String eventId;
    private String googleGeo;
    private LocalDateTime endTime;
    private LocalDateTime lastChangeTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventBlockEntity that = (EventBlockEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(prevPosBlock, that.prevPosBlock) && Objects.equals(vehicleStat, that.vehicleStat) && Objects.equals(linkVehicle, that.linkVehicle) && Objects.equals(startTime, that.startTime) && Objects.equals(createTime, that.createTime) && Objects.equals(positions, that.positions) && Objects.equals(driverId, that.driverId) && Objects.equals(eventId, that.eventId) && Objects.equals(googleGeo, that.googleGeo) && Objects.equals(endTime, that.endTime) && Objects.equals(lastChangeTime, that.lastChangeTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prevPosBlock, vehicleStat, linkVehicle, startTime, createTime, positions, driverId, eventId, googleGeo, endTime, lastChangeTime);
    }
}
