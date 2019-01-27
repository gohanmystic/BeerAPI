package com.BeerAPI.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BeerAPI.common.exception.NotFoundException;
import com.BeerAPI.dto.prm.beer.PrmFetchBeer;
import com.BeerAPI.dto.req.beer.ReqCreateBeer;
import com.BeerAPI.dto.req.beer.ReqDeleteBeer;
import com.BeerAPI.dto.req.beer.ReqFetchBeer;
import com.BeerAPI.dto.req.beer.ReqUpdateBeer;
import com.BeerAPI.entity.Beer;
import com.BeerAPI.repository.BeerRepository;
import com.BeerAPI.service.BeerService;

@Service
public class BeerServiceImpl implements BeerService {

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Beer> fetchBeer(ReqFetchBeer reqFetchBeer) {
        return beerRepository.fetchBeer(
                modelMapper.map(reqFetchBeer, PrmFetchBeer.class));
    }

    @Override
    public Beer createBeer(ReqCreateBeer reqCreateBeer) {
        Beer beer = modelMapper.map(reqCreateBeer, Beer.class);
        return beerRepository.save(beer);
    }

    @Override
    public Beer updateBeer(ReqUpdateBeer reqUpdateBeer) {
        Optional<Beer> beer = beerRepository.findById(reqUpdateBeer.getBeerId());

        if (!beer.isPresent()) {
            throw new NotFoundException();
        }

        return beerRepository.save(
                modelMapper.map(reqUpdateBeer, Beer.class));
    }

    @Override
    public boolean deleteBeer(ReqDeleteBeer reqDeleteBeer) {
        Integer id = reqDeleteBeer.getBeerId();
        beerRepository.deleteById(id);
        return !beerRepository.findById(id).isPresent();
    }

    @Override
    public Beer findBeerById(Integer beerId) {
        Optional<Beer> beer = beerRepository.findById(beerId);
        if (!beer.isPresent()) {
            throw new NotFoundException();
        }
        return beer.get();
    }

}
