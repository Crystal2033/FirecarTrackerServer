package com.aqulasoft.fireman.tracker.database.repositories;

import com.aqulasoft.fireman.tracker.database.entities.EventBlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventBlockRepository extends JpaRepository<EventBlockEntity, String> {
}
