package com.BeerAPI.dto.res.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResAuthority {

    @JsonProperty("role")
    private String role;
}
