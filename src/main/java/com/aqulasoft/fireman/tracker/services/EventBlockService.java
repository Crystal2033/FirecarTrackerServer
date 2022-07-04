package com.aqulasoft.fireman.tracker.services;

import com.aqulasoft.fireman.tracker.models.EventBlockRequest;
import org.springframework.stereotype.Service;

@Service
public interface EventBlockService {
    EventBlockRequest addEventBlock(EventBlockRequest eventBlockRequest);
}
