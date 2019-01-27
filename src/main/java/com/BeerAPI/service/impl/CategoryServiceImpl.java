package com.BeerAPI.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BeerAPI.common.exception.NotFoundException;
import com.BeerAPI.dto.prm.category.PrmFetchCategory;
import com.BeerAPI.dto.req.category.ReqCreateCategory;
import com.BeerAPI.dto.req.category.ReqDeleteCategory;
import com.BeerAPI.dto.req.category.ReqFetchCategory;
import com.BeerAPI.dto.req.category.ReqUpdateCategory;
import com.BeerAPI.entity.Category;
import com.BeerAPI.repository.CategoryRepository;
import com.BeerAPI.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Category> fetchCategory(ReqFetchCategory reqFetchCategory) {
        return categoryRepository.fetchCategory(
                modelMapper.map(reqFetchCategory, PrmFetchCategory.class));
    }

    @Override
    public Category createCategory(ReqCreateCategory reqCreateCategory) {
        Category category = modelMapper.map(reqCreateCategory, Category.class);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(ReqUpdateCategory reqUpdateCategory) {
        Optional<Category> category = categoryRepository.findById(reqUpdateCategory.getCategoryId());

        if (!category.isPresent()) {
            throw new NotFoundException();
        }

        return categoryRepository.save(
                modelMapper.map(reqUpdateCategory, Category.class));
    }

    @Override
    public boolean deleteCategory(ReqDeleteCategory reqDeleteCategory) {
        Integer id = reqDeleteCategory.getCategoryId();
        categoryRepository.deleteById(id);
        return !categoryRepository.findById(id).isPresent();
    }

    @Override
    public Category findCategoryById(Integer categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new NotFoundException();
        }
        return category.get();
    }
}
