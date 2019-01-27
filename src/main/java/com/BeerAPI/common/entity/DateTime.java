package com.BeerAPI.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DateTime {

    @JsonProperty("date")
    private DateTimeProperties date;

    @JsonProperty("epoc")
    private Long timestamp;

    @JsonProperty("formatted")
    private String formatted;
}

