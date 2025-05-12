package com.dam.web_cocina.common.exceptions;

public class InvalidCredentialsException extends ApiCodeException {
    public static final int ERROR_CODE = 1003;
    private static final String MESSAGE = "Correo o contraseña inválidos";

    public InvalidCredentialsException() {
        super(MESSAGE, ERROR_CODE);
    }
}
