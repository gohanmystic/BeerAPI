package com.BeerAPI.common.properties;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("token")
public class TokenProperties {

    @NotNull
    private int refreshTokenTimeout;

    @NotNull
    private int accessTokenTimeout;
}
