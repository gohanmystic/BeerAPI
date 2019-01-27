package com.BeerAPI.common.enums;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender implements CodeEnum {

    MALE("0", ""),
    FEMALE("1", "");

    private final String value;
    private final String name;

    static public Gender getByValue(String value) {

        Gender result = null;

        if (StringUtils.isNotBlank(value)) {
            for (Gender gender : values()) {
                if (gender.getValue().equals(value)) {
                    result = gender;
                }
            }
        }

        return result;
    }
}
