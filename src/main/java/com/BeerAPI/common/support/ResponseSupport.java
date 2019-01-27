package com.BeerAPI.common.support;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.BeerAPI.common.ApiResponse;
import com.BeerAPI.common.Error;
import com.BeerAPI.common.enums.DateTimeFormat;
import com.BeerAPI.common.enums.MessageCode;
import com.BeerAPI.common.enums.StatusCode;

@Component
public class ResponseSupport {

    private static final String VALIDATION_MESSAGE = StatusCode.VALIDATION_ERROR.getName();

    @Autowired
    @Qualifier("validationMessageSource")
    private MessageSource messageSource;

    @Autowired
    private DateTimeSupport dateTimeSupport;

    public ApiResponse<?> fetchSuccess(final Object data, final MessageCode messageCode) {

        return fetchNormal(StatusCode.SUCCESS, messageCode, data);
    }

    public ApiResponse<?> fetchSuccess(final MessageCode messageCode) {

        return fetchNormal(StatusCode.SUCCESS, messageCode, null);
    }

    public ApiResponse<?> fetchError(final StatusCode statusCode, final MessageCode messageCode) {

        String message = messageSource.getMessage(messageCode.getValue(),
                                                  null,
                                                  Locale.getDefault());

        return response(statusCode, message, null, null);
    }

    public ApiResponse<?> fetchError(final StatusCode statusCode, final String message) {

        return response(statusCode, message, null, null);
    }

    public ApiResponse<?> fetchError(final List<Error> errors) {

        return response(StatusCode.VALIDATION_ERROR,
                        VALIDATION_MESSAGE,
                        null,
                        errors);
    }

    public ResponseEntity<?> fetchCreate() {

        return new ResponseEntity<>(response(StatusCode.SUCCESS,
                messageSource.getMessage(MessageCode.SUCCESS_CREATE.getValue(), null, Locale.getDefault()),
                null,
                null),
                HttpStatus.CREATED);
    }

    private ApiResponse<?> fetchNormal(final StatusCode statusCode,
                                       final MessageCode messageCode,
                                       final Object data) {

        String message = messageSource.getMessage(messageCode.getValue(), null, Locale.getDefault());

        return response(statusCode, message, data, null);
    }

    private ApiResponse<?> response(final StatusCode statusCode,
                                    final String message,
                                    final Object data,
                                    final List<Error> errors) {
        String importance = null;
        switch (statusCode.getValue()) {
            case "200":
                importance = "1";
                break;
            case "2000":
                importance = "3";
                break;
            default:
                importance = "4";
                break;
        }
        return ApiResponse.builder()
                          .timestamp(dateTimeSupport.convertDateTimeToString(LocalDateTime.now(),
                                                                             DateTimeFormat.SLASH_YYYY_MM_DD_HH_MM_SS))
                          .importance(importance)
                          .statusCode(statusCode)
                          .message(message)
                          .data(data)
                          .errors(errors)
                          .build();
    }
}
