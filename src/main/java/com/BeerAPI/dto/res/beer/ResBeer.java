package com.BeerAPI.dto.res.beer;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResBeer {

    @JsonProperty("beer_id")
    private String beerId;

    @JsonProperty("beer_name")
    private String beerName;

    @JsonProperty("country")
    private String country;

    @JsonProperty("price")
    private String price;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category_id")
    private String categoryId;
}
