package com.BeerAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BeerAPI.entity.Category;
import com.BeerAPI.repository.custom.CategoryRepositoryCustom;

public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryRepositoryCustom {
}
