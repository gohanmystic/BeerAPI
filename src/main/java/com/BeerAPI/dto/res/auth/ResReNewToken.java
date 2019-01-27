package com.BeerAPI.dto.res.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResReNewToken {


    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("access_token_expire_date")
    private String accessTokenExpireDate;
}
