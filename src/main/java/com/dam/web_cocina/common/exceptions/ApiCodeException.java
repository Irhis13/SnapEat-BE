package com.dam.web_cocina.common.exceptions;

import lombok.Getter;

@Getter
public class ApiCodeException extends RuntimeException {
    private final Integer errorCode;

    public ApiCodeException(final String message, final Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
