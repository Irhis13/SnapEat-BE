package com.dam.web_cocina.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiCodeError(
        Integer errorCode,
        String message,
        List<ApiParamErrorField> params
) {
}
