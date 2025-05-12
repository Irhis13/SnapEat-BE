package com.dam.web_cocina.common.exceptions;

public class UnauthorizedAccessException extends ApiCodeException {
    public static final int ERROR_CODE = 1002;

    private static final String EDIT_MESSAGE = "No tiene permisos para editar esta receta";
    private static final String DELETE_MESSAGE = "No tiene permisos para eliminar esta receta";

    private UnauthorizedAccessException(String message) {
        super(message, ERROR_CODE);
    }

    public static UnauthorizedAccessException forEdit() {
        return new UnauthorizedAccessException(EDIT_MESSAGE);
    }

    public static UnauthorizedAccessException forDelete() {
        return new UnauthorizedAccessException(DELETE_MESSAGE);
    }

    public static UnauthorizedAccessException forNotAuthenticated() {
        return new UnauthorizedAccessException("Debe estar autenticado para realizar esta acción");
    }
}
