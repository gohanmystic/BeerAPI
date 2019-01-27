package com.BeerAPI.dto.req.category;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReqDeleteCategory {

    @JsonProperty("category_id")
    private Integer categoryId;
}
