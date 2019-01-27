package com.BeerAPI.configuration;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.BeerAPI.common.entity.DateTime;
import com.BeerAPI.common.enums.DateTimeFormat;
import com.BeerAPI.common.support.DateTimeSupport;

@Configuration
public class ModelMapperConfig {

    @Autowired
    private DateTimeSupport dateTimeSupport;

    @Bean
    protected ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter(trimString());
        modelMapper.addConverter(convertStringToUtilDate());
        modelMapper.addConverter(convertUtilDateToString());
        modelMapper.addConverter(convertCommonDateTimeToUtilDate());
        modelMapper.addConverter(convertUtilDateToCommonDateTime());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }

    private AbstractConverter<String, String> trimString() {
        return new AbstractConverter<String, String>() {

            @Override
            protected String convert(String source) {
                return StringUtils.trim(source);
            }
        };
    }

    private AbstractConverter<String, Date> convertStringToUtilDate() {
        return new AbstractConverter<String, Date>() {

            @Override
            protected Date convert(String source) {

                return dateTimeSupport.convertStringToUtilDate(source, DateTimeFormat.SLASH_YYYYMMDD);
            }
        };
    }

    private AbstractConverter<Date, String> convertUtilDateToString() {
        return new AbstractConverter<Date, String>() {

            @Override
            protected String convert(Date source) {

                return dateTimeSupport.convertUtilDateToString(source, DateTimeFormat.SLASH_YYYYMMDD);
            }
        };
    }

    private AbstractConverter<DateTime, Date> convertCommonDateTimeToUtilDate() {
        return new AbstractConverter<DateTime, Date>() {

            @Override
            protected Date convert(DateTime source) {

                return dateTimeSupport.convertCommonDateTimeToUtilDate(source, DateTimeFormat.SLASH_YYYYMMDD);
            }
        };
    }

    private AbstractConverter<Date, DateTime> convertUtilDateToCommonDateTime() {
        return new AbstractConverter<Date, DateTime>() {

            @Override
            protected DateTime convert(Date source) {

                return dateTimeSupport.convertUtilDateToCommonDateTime(source, DateTimeFormat.SLASH_YYYYMMDD);
            }
        };
    }
}
