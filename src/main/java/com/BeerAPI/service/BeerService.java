package com.BeerAPI.service;

import java.util.List;

import com.BeerAPI.dto.req.beer.ReqCreateBeer;
import com.BeerAPI.dto.req.beer.ReqDeleteBeer;
import com.BeerAPI.dto.req.beer.ReqFetchBeer;
import com.BeerAPI.dto.req.beer.ReqUpdateBeer;
import com.BeerAPI.entity.Beer;

public interface BeerService {

    List<Beer> fetchBeer(ReqFetchBeer reqFetchBeer);

    Beer createBeer(ReqCreateBeer reqCreateBeer);

    Beer updateBeer(ReqUpdateBeer reqUpdateBeer);

    Beer findBeerById(Integer beerId);

    boolean deleteBeer(ReqDeleteBeer reqDeleteBeer);
}
