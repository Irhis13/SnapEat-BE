package com.dam.web_cocina.common.exceptions;

public class UsernameUsedException extends ApiCodeException {
    public static final int ERROR_CODE = 1004;
    private static final String MESSAGE = "El nombre de usuario '%s' ya está en uso";

    public UsernameUsedException(String username) {
        super(String.format(MESSAGE, username), ERROR_CODE);
    }
}
