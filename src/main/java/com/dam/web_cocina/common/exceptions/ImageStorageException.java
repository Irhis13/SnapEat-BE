package com.dam.web_cocina.common.exceptions;

public class ImageStorageException extends ApiCodeException {
    public static final int ERROR_CODE = 1005;
    private static final String MESSAGE = "Error al guardar la imagen: %s";

    public ImageStorageException(String detail) {
        super(String.format(MESSAGE, detail), ERROR_CODE);
    }
}
