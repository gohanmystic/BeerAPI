package com.BeerAPI.common.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.BeerAPI.common.support.ValidationSupport;

public abstract class BaseValidator<T> {

    @Autowired
    protected ValidationSupport validationSupport;
}
