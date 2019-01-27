package com.BeerAPI.dto.req.user;

import com.BeerAPI.common.annotation.FieldName;
import com.BeerAPI.common.entity.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReqCreateUser {

    @FieldName("Username")
    @JsonProperty("username")
    private String username;

    @FieldName("Password")
    @JsonProperty("password")
    private String password;

    @FieldName("FullName")
    @JsonProperty("full_name")
    private String fullName;

    @FieldName("Gender")
    @JsonProperty("gender")
    private String gender;

    @FieldName("DateOfBirth")
    @JsonProperty("date_of_birth")
    private DateTime dateOfBirth;

    @JsonProperty("phone")
    private String phone;

    @FieldName("Email")
    @JsonProperty("email")
    private String email;

}
