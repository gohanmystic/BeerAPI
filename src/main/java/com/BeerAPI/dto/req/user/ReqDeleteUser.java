package com.BeerAPI.dto.req.user;

import com.BeerAPI.common.annotation.FieldName;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReqDeleteUser {

    @FieldName("UserId")
    @JsonProperty("user_id")
    private Integer userId;
}
