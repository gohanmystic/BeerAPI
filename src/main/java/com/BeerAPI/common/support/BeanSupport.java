package com.BeerAPI.common.support;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

@Component
public class BeanSupport {

    public void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public String[] getNullPropertyNames (Object source) {

        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] propDesList = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();

        for(PropertyDescriptor propDesc : propDesList) {
            Object srcValue = src.getPropertyValue(propDesc.getName());

            if (srcValue == null) {
                emptyNames.add(propDesc.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
