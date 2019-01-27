package com.BeerAPI.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageCode implements CodeEnum {

    SUCCESS_LOGIN("msg000", ""),
    SUCCESS_DATA("msg001", ""),
    SUCCESS_CREATE("msg002", ""),
    SUCCESS_GET_DETAIL("msg003", ""),
    SUCCESS_DELETE("msg004", ""),
    SUCCESS_UPDATE("msg005", ""),
    ERROR_LOGIN("msg010", ""),
    ERROR_DATA("msg011", ""),
    ERROR_CREATE("msg012", ""),
    ERROR_GET_DETAUL("msg013", ""),
    ERROR_DELETE("msg014", ""),
    ERROR_UPDATE("msg015", ""),
    ERROR_OBJECT_EXISTED("objectExisted", ""),
    ERROR_OBJECT_NOT_EXISTED("objectNotExisted", ""),
    SYSTEM_FAILURE_MESSAGE("systemError", ""),
    UNAUTHORIZED_MESSAGE("unauthorized", ""),
    USER_PASSWORD_ERROR("userPasswordError", ""),
    USERNAME_NOT_FOUND("userNameNotFound", ""),
    TOKEN_EXPIRED("tokenExpired", ""),
    TOKEN_FAILURED("tokenFailured", "");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }
}
