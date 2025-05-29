package com.dam.web_cocina.common.exceptions;

public class InvalidGeneroException extends RuntimeException {
    public InvalidGeneroException(String genero) {
        super("Género no válido: " + genero + ". Valores permitidos: HOMBRE, MUJER, OTRO.");
    }
}
