package com.dam.web_cocina.common.exceptions;

import java.util.Arrays;

public class EntityNotFoundException extends ApiCodeException {
    public static final int ERROR_CODE = 1001;
    private static final String MESSAGE = "No se encontró %s con %s: %s";

    public EntityNotFoundException(String entityName, String fieldName, Object value) {
        super(String.format(MESSAGE, entityName, fieldName, value), ERROR_CODE);
    }

    public EntityNotFoundException(String entityName, String[] fieldNames, Object[] values) {
        super(String.format(
                MESSAGE,
                entityName,
                String.join(", ", fieldNames),
                String.join(", ", Arrays.stream(values).map(Object::toString).toArray(String[]::new))
        ), ERROR_CODE);
    }
}
