package com.BeerAPI.dto.req.beer;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReqDeleteBeer {

    @JsonProperty("beer_id")
    private Integer beerId;
}
