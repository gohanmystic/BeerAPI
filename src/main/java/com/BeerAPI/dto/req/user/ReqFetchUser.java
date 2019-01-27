package com.BeerAPI.dto.req.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReqFetchUser {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("full_name")
    private String fullName;
}
