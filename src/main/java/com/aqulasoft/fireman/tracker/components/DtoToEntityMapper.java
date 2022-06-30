package com.aqulasoft.fireman.tracker.components;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DtoToEntityMapper {

    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

