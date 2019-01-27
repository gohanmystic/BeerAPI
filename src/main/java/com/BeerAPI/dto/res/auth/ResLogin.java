package com.BeerAPI.dto.res.auth;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResLogin {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("access_token_expired_date")
    private String accessTokenExpiredDate;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("refresh_token_expired_date")
    private String refreshTokenExpiredDate;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("permissions")
    private List<ResAuthority> permissions;
}
