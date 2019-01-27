package com.BeerAPI.dto.req.category;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReqCreateCategory {

    @JsonProperty("category_name")
    private String categoryName;
}
