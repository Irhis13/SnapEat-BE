package com.dam.web_cocina.common.exceptions;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(Long id) {
        super("No existe receta con id: " + id);
    }
}
