package com.BeerAPI.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"errorCode", "errorItem", "errorMessage"})
public class Error {
    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_item")
    private String errorItem;

    @JsonProperty("error_message")
    private String errorMessage;
}
