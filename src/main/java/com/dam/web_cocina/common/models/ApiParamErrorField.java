package com.dam.web_cocina.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiParamErrorField(
        String field,
        String message
) implements Serializable {
}
