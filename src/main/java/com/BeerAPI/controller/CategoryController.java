package com.BeerAPI.controller;

import java.lang.reflect.Type;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BeerAPI.common.enums.MessageCode;
import com.BeerAPI.common.enums.StatusCode;
import com.BeerAPI.common.support.ResponseSupport;
import com.BeerAPI.dto.req.category.ReqCreateCategory;
import com.BeerAPI.dto.req.category.ReqDeleteCategory;
import com.BeerAPI.dto.req.category.ReqFetchCategory;
import com.BeerAPI.dto.req.category.ReqUpdateCategory;
import com.BeerAPI.dto.res.category.ResCategory;
import com.BeerAPI.service.CategoryService;

@RestController
@RequestMapping( value = "/api/v1/category", produces = MediaType.APPLICATION_JSON_VALUE )
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ResponseSupport responseSupport;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity<?> findCategoryById(@PathVariable Integer categoryId ) {

        ResCategory resCategory =
                modelMapper.map(categoryService.findCategoryById(categoryId),
                        ResCategory.class);

        return ResponseEntity
                .ok(responseSupport.fetchSuccess(resCategory, MessageCode.SUCCESS_GET_DETAIL));
    }

    @PostMapping(value = "list")
    public ResponseEntity<?> findAllCategory(
            @Valid @RequestBody ReqFetchCategory reqFetchCategory) {

        Type type = new TypeToken<List<ResCategory>>(){}.getType();

        List<ResCategory> categoryResponseList =
                modelMapper.map(categoryService.fetchCategory(reqFetchCategory),
                                type);

        return ResponseEntity
                .ok(responseSupport.fetchSuccess(categoryResponseList, MessageCode.SUCCESS_DATA));
    }

    @PostMapping(value = "create")
    public ResponseEntity<?> saveEmployee(
            @Valid @RequestBody ReqCreateCategory reqCreateCategory) {

        ResCategory resCategory =
                modelMapper.map(categoryService.createCategory(reqCreateCategory),
                                ResCategory.class);

        return ResponseEntity
                .ok(responseSupport.fetchSuccess(resCategory, MessageCode.SUCCESS_CREATE));
    }

    @DeleteMapping(value = "/{categoryId}")
    public ResponseEntity<?> deleteCategoryById(
            @Valid @RequestBody ReqDeleteCategory reqDeleteCategory) {

        boolean isDeleted = categoryService.deleteCategory(reqDeleteCategory);

        if (isDeleted) {

            return ResponseEntity
                    .ok(responseSupport.fetchSuccess(MessageCode.SUCCESS_DELETE));
        } else {

            return ResponseEntity
                    .ok(responseSupport.fetchError(StatusCode.SYSTEM_FAILURE, MessageCode.ERROR_DELETE));
        }
    }

    @PutMapping(value = "/{categoryId}")
    public ResponseEntity<?> updateCategory(
            @Valid @RequestBody ReqUpdateCategory reqUpdateCategory) {

        ResCategory resCategory =
                modelMapper.map(categoryService.updateCategory(reqUpdateCategory),
                                ResCategory.class);

        return ResponseEntity
                .ok(responseSupport.fetchSuccess(resCategory, MessageCode.SUCCESS_UPDATE));
    }

}
