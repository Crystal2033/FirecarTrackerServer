package com.aqulasoft.fireman.tracker.database.repositories;

import com.aqulasoft.fireman.tracker.database.entities.VehiclePositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehiclePositionRepository extends JpaRepository<VehiclePositionEntity, String> {
    Optional<VehiclePositionEntity> findOptionalById(String betId);
    List<VehiclePositionEntity> findAllByPosBlockId(String id);
}
