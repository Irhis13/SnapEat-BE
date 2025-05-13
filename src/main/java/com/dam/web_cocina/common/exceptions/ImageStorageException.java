package com.dam.web_cocina.common.exceptions;

public class ImageStorageException extends ApiCodeException {
    public static final int ERROR_CODE = 1005;

    private ImageStorageException(String message) {
        super(message, ERROR_CODE);
    }

    public static ImageStorageException forTooLarge(long maxBytes) {
        return new ImageStorageException("La imagen excede el tamaño máximo permitido de " + (maxBytes / (1024 * 1024)) + "MB.");
    }

    public static ImageStorageException forUnsupportedFormat(String format) {
        return new ImageStorageException("Formato de imagen no soportado: " + format);
    }

    public static ImageStorageException forSavingFailure(String detail) {
        return new ImageStorageException("Error al guardar la imagen: " + detail);
    }
}
