package com.dam.web_cocina.common.exceptions;

import com.dam.web_cocina.common.models.ApiParamErrorField;
import lombok.Getter;

import java.util.List;

@Getter
public class ApiParamsException extends RuntimeException {
    private final List<ApiParamErrorField> params;

    public ApiParamsException(List<ApiParamErrorField> params) {
        super("Error de validación en parámetros");
        this.params = params;
    }
}