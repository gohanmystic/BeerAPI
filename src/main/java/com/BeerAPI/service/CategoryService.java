package com.BeerAPI.service;

import java.util.List;

import com.BeerAPI.dto.req.category.ReqCreateCategory;
import com.BeerAPI.dto.req.category.ReqDeleteCategory;
import com.BeerAPI.dto.req.category.ReqFetchCategory;
import com.BeerAPI.dto.req.category.ReqUpdateCategory;
import com.BeerAPI.entity.Category;

public interface CategoryService {

    List<Category> fetchCategory(ReqFetchCategory reqFetchCategory);

    Category createCategory(ReqCreateCategory reqCreateCategory);

    Category updateCategory(ReqUpdateCategory reqUpdateCategory);

    Category findCategoryById(Integer categoryId);

    boolean deleteCategory(ReqDeleteCategory reqDeleteCategory);
}
