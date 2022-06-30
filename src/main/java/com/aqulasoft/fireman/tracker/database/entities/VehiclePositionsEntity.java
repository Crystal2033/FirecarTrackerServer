//package com.aqulasoft.fireman.tracker.database.entities;
//
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//
//@Entity
//@Getter
//@Setter
//@Table(name="VehiclePositions")
//public class VehiclePositionsEntity {
//    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid") // генерация id
//    @Column(length = 32, updatable = false, nullable = false)
//    private String id;
//
//    private float latitude;
//    private float longitude;
//
//    @ManyToOne
//    @JoinColumn(name = "pos_block_id")
//    private EventBlockEntity posBlock;
//
//    private LocalDateTime createTime;
//    private LocalDateTime lastChangeTime;
//
//}
