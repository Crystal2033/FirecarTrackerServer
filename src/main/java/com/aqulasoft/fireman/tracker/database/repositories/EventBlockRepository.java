package com.aqulasoft.fireman.tracker.database.repositories;

import com.aqulasoft.fireman.tracker.database.entities.EventBlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventBlockRepository extends JpaRepository<EventBlockEntity, String> {
    Optional<EventBlockEntity> findOptionalById(String id);

    List<EventBlockEntity> findAllByVehicleStatId(String id);

    Optional<EventBlockEntity> findOptionalByEventId(String id);
}
