package com.dam.web_cocina.common.exceptions;

public class RecipeNotFoundException extends ApiCodeException {
    public static final int ERROR_CODE = 1001;
    private static final String MESSAGE = "No existe receta con ID: %d";

    public RecipeNotFoundException(Long id) {
        super(String.format(MESSAGE, id), ERROR_CODE);
    }
}
