package com.BeerAPI.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Roles implements CodeEnum {

    ROLE_ADMIN("ROLE_ADMIN", ""),
    ROLE_USER("ROLE_USER", ""),
    REFRESH_TOKEN("REFRESH_TOKEN", ""),
    ACCESS_TOKEN("ACCESS_TOKEN", "");

    private final String value;
    private final String name;
}
