package com.dam.web_cocina.common.exceptions;

public class UserNotFoundException extends ApiCodeException {
    public static final int ERROR_CODE = 1003;
    private static final String MESSAGE = "No se encontró un usuario con el email: %s";

    public UserNotFoundException(String email) {
        super(String.format(MESSAGE, email), ERROR_CODE);
    }
}