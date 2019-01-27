package com.BeerAPI.common.exception;

import com.BeerAPI.common.annotation.ResponseStatus;
import com.BeerAPI.common.enums.StatusCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ResponseStatus(code = StatusCode.TOKEN_FAILURED)
public class TokenFailuredException extends RuntimeException {

    private static final long serialVersionUID = -2719667540409232859L;

    public TokenFailuredException(final String msg) {
        super(msg);
        log.error(msg);
    }

    public TokenFailuredException(final String msg, final Throwable throwable) {
        super(msg, throwable);
        log.error(msg, throwable);
    }
}
