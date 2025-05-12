package com.dam.web_cocina.common.exceptions;

public class EmailUsedException extends ApiCodeException {
    public static final int ERROR_CODE = 1004;
    public static final String MESSAGE = "Ya existe un usuario con ese correo: %s";

    public EmailUsedException(String email) {
        super(String.format(MESSAGE, email), ERROR_CODE);
    }
}
