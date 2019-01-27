package com.BeerAPI.dto.res.user;

import com.BeerAPI.common.entity.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResUser {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("date_of_birth")
    private DateTime dateOfBirth;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;
}
