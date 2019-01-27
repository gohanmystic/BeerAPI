package com.BeerAPI.repository.custom;

import java.util.List;

import com.BeerAPI.dto.prm.category.PrmFetchCategory;
import com.BeerAPI.entity.Category;

public interface CategoryRepositoryCustom {
    List<Category> fetchCategory(PrmFetchCategory fetchParam);
}
