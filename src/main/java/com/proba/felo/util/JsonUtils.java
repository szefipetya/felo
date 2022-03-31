package com.proba.felo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonUtils {
    @Bean(name = "jsonMapper")
    public ObjectMapper getJsonMapper() {
        return new ObjectMapper();
    }

    @Bean(name = "modelMapper")
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
