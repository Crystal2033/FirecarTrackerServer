package com.aqulasoft.fireman.tracker.components;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DtoToEntityMapper {
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

