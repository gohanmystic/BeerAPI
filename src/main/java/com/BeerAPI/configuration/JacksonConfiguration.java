package com.BeerAPI.configuration;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.BeerAPI.common.enums.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {

        return new Jackson2ObjectMapperBuilder() {

            @Override
            public void configure(ObjectMapper objectMapper) {
                super.configure(objectMapper);
                objectMapper.setSerializationInclusion(Include.NON_NULL);
                objectMapper.setSerializationInclusion(Include.NON_DEFAULT);
                objectMapper.setDateFormat(new SimpleDateFormat(
                                                        DateTimeFormat.SLASH_YYYYMMDD.getValue()));
                objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
                objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            }
        };
    }
}
