package com.BeerAPI.dto.res.category;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResCategory {

    @JsonProperty("category_id")
    private String categoryId;

    @JsonProperty("category_name")
    private String categoryName;
}
