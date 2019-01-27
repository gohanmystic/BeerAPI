package com.BeerAPI.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DateTimeFormat implements CodeEnum {

    YYYYMMDD("yyyyMMdd", ""),
    SLASH_YYYYMMDD("yyyy/MM/dd", ""),
    YYYYMMDDHHMMSS("yyyyMMddHHmmss", ""),
    SLASH_YYYY_MM_DD_HH_MM_SS("yyyy/MM/dd HH:mm:ss", ""),
    SLASH_YYYY_MM_DD_HH_MM_SS_FF("yyyy/MM/dd HH:mm:ss.SSS", ""),
    DASH_YYYYMMDD("yyyy-MM-dd", "");

    private String value;
    private String name;
}
