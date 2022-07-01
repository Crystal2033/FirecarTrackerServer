package com.aqulasoft.fireman.tracker.database.repositories;

import com.aqulasoft.fireman.tracker.database.entities.EventBlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventBlockRepository extends JpaRepository<EventBlockEntity, String> {

    Optional<EventBlockEntity> findOptionalById(String id);
    List<EventBlockEntity> findAllByVehicleId(String id);
}
