package com.aqulasoft.fireman.tracker.database.entities;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

    @OneToOne (mappedBy = "vehicleId")
    @JoinColumn (name="pos_block_head_id")
    private PositionsBlockEntity posBlockHead;

    private int vehicleId;


}


