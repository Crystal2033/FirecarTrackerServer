package com.aqulasoft.fireman.tracker.database.repositories;

import com.aqulasoft.fireman.tracker.database.entities.VehiclePositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiclePositionRepository extends JpaRepository<VehiclePositionEntity, String> {
    Optional<VehiclePositionEntity> findOptionalById(String id);

    List<VehiclePositionEntity> findAllByPosBlockId(String id);
}
