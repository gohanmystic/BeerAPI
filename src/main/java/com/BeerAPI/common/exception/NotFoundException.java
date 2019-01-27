package com.BeerAPI.common.exception;

import com.BeerAPI.common.annotation.ResponseStatus;
import com.BeerAPI.common.enums.StatusCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ResponseStatus(code = StatusCode.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super("");
    }

    public NotFoundException(final String message) {
        super(message);
        log.error(message);
    }

    public NotFoundException(final String message, final Throwable throwable) {
        super(message, throwable);
        log.error(message, throwable);
    }
}
