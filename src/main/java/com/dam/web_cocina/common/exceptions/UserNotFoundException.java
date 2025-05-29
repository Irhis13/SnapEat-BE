package com.dam.web_cocina.common.exceptions;

public class UserNotFoundException extends ApiCodeException {
    public static final int ERROR_CODE = 1003;
    private static final String MESSAGE = "No se encontró un usuario con %s: %s";

    public UserNotFoundException(String field, String value) {
        super(String.format(MESSAGE, field, value), ERROR_CODE);
    }
}