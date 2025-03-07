package com.karacheban.demo.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponseWrapper<T> {
    @JsonProperty("result")
    private T result;

    @JsonProperty("isCompletedSuccessfully")
    private boolean isCompletedSuccessfully;
}