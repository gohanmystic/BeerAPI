package com.BeerAPI.common;

import java.util.List;

import com.BeerAPI.common.enums.StatusCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@Data
@JsonPropertyOrder({"timestamp", "importance", "status_code", "message", "data", "validation_errors"})
public class ApiResponse<T> {

    private String timestamp;

    private String importance;

    @JsonProperty("status_code")
    private StatusCode statusCode;

    private String message;

    private T data;

    @JsonProperty("validation_errors")
    private List<Error> errors;

    @Builder
    public ApiResponse(final String timestamp,
                       final String importance,
                       final StatusCode statusCode,
                       final String message,
                       final T data,
                       final List<Error> errors) {
        this.timestamp = timestamp;
        this.importance = importance;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }
}
