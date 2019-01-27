package com.BeerAPI.repository.custom;

import java.util.List;

import com.BeerAPI.dto.prm.beer.PrmFetchBeer;
import com.BeerAPI.entity.Beer;

public interface BeerRepositoryCustom {
    List<Beer> fetchBeer(PrmFetchBeer fetchParam);
}
