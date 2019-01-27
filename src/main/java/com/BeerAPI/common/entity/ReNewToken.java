package com.BeerAPI.common.entity;

import java.util.Date;

import com.BeerAPI.common.enums.MessageCode;
import com.BeerAPI.common.enums.StatusCode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReNewToken {

    private String accessToken;

    private Date accessTokenExpiredDate;

    private MessageCode errorMessage;

    private StatusCode errorCode;
}
