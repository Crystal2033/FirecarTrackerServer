package com.aqulasoft.fireman.tracker.database.repositories;

import com.aqulasoft.fireman.tracker.database.entities.VehicleStatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleStatRepository extends JpaRepository<VehicleStatEntity, String> {
    Optional<VehicleStatEntity> findOptionalById(String id);

    Optional<VehicleStatEntity> findOptionalByVehicleId(String vehicleId);
}
