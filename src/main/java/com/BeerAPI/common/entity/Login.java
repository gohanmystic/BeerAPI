package com.BeerAPI.common.entity;

import java.util.Date;
import java.util.List;

import com.BeerAPI.common.enums.MessageCode;
import com.BeerAPI.common.enums.StatusCode;
import com.BeerAPI.entity.Authority;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Login {

    private String accessToken;

    private Date accessTokenExpiredDate;

    private String refreshToken;

    private Date refreshTokenExpiredDate;

    private MessageCode errorMessage;

    private StatusCode errorCode;

    private List<Authority> permissions;
}
