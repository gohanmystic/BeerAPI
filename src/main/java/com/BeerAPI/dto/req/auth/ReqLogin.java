package com.BeerAPI.dto.req.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReqLogin {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
