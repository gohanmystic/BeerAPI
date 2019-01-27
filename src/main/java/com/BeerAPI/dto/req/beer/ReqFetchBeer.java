package com.BeerAPI.dto.req.beer;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReqFetchBeer {

    @JsonProperty("beer_id")
    private Integer beerId;

    @JsonProperty("beer_name")
    private String beerName;
}
