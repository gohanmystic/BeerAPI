package com.BeerAPI.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DateTimeProperties {

    @JsonProperty("day")
    private int day;

    @JsonProperty("month")
    private int month;

    @JsonProperty("year")
    private int year;
}
