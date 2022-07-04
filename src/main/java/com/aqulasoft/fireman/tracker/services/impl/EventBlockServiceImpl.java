package com.aqulasoft.fireman.tracker.services.impl;

import com.aqulasoft.fireman.tracker.models.EventBlockRequest;
import com.aqulasoft.fireman.tracker.services.EventBlockService;
import org.springframework.stereotype.Service;

@Service
public class EventBlockServiceImpl implements EventBlockService {
    @Override
    public EventBlockRequest addEventBlock(EventBlockRequest eventBlockRequest) {
        return new EventBlockRequest();
    }
}
