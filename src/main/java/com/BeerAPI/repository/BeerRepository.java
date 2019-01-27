package com.BeerAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BeerAPI.entity.Beer;
import com.BeerAPI.repository.custom.BeerRepositoryCustom;

public interface BeerRepository extends JpaRepository<Beer, Integer>, BeerRepositoryCustom {

}
