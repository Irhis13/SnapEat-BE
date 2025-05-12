package com.dam.web_cocina.common.handlers;

import com.dam.web_cocina.common.exceptions.ApiCodeException;
import com.dam.web_cocina.common.exceptions.ApiParamsException;
import com.dam.web_cocina.common.exceptions.EntityNotFoundException;
import com.dam.web_cocina.common.exceptions.UnauthorizedAccessException;
import com.dam.web_cocina.common.models.ApiCodeError;
import com.dam.web_cocina.common.models.ApiParamErrorField;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiCodeError> handleGenericException(final Exception ex) {
        logger.error("{} -> {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);

        final ApiCodeError apiCodeError = new ApiCodeError(
                null,
                "Internal Server Error",
                null
        );
        return new ResponseEntity<>(apiCodeError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Will get invoke when invalid data is sent in rest request object.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiCodeError> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex
    ) {
        final List<ApiParamErrorField> apiParamErrorFields = new ArrayList<>();
        for (final FieldError error : ex.getFieldErrors()) {
            apiParamErrorFields.add(
                    new ApiParamErrorField(
                            error.getField(),
                            error.getDefaultMessage()
                    )
            );
        }
        final ApiCodeError response = new ApiCodeError(
                null,
                null,
                apiParamErrorFields
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Will get invoke when invalid path variable is sent in rest request.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiCodeError> handleConstraintViolationException(
            final ConstraintViolationException ex
    ) {
        final List<ApiParamErrorField> paramErrors = new ArrayList<>();
        for (final ConstraintViolation<?> error : ex.getConstraintViolations()) {
            paramErrors.add(
                    new ApiParamErrorField(
                            error.getPropertyPath()
                                    .toString()
                                    .split("\\.")[1],
                            error.getMessage()
                    )
            );
        }
        final ApiCodeError response = new ApiCodeError(
                null,
                null,
                paramErrors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Will get invoke when invalid object fields are set.
     */
    @ExceptionHandler(ApiParamsException.class)
    public ResponseEntity<ApiCodeError> handleApiParamsException(
            final ApiParamsException ex
    ) {
        final ApiCodeError response = new ApiCodeError(
                null,
                null,
                ex.getParams()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Will get invoke when unauthorized exception is thrown.
     */
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiCodeError> handleUnauthorizedException(final RuntimeException ex) {
        final ApiCodeError apiCodeError = new ApiCodeError(
                null,
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiCodeError, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Will get invoke when an EntityNotFoundException is thrown.
     */
    @ExceptionHandler({EntityNotFoundException.class, })
    public ResponseEntity<ApiCodeError> handleNotFoundException(final RuntimeException ex) {
        final ApiCodeError apiCodeError = new ApiCodeError(
                null,
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiCodeError, HttpStatus.NOT_FOUND);
    }

    /**
     * Will get invoke when invalid path variable is sent in rest request.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiCodeError> handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException ex
    ) {
        final ApiCodeError apiCodeError = new ApiCodeError(
                null,
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiCodeError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Will get invoke when max request size is exceeded.
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(
            final MaxUploadSizeExceededException ex
    ) {
        final ApiCodeError apiCodeError = new ApiCodeError(
                null,
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiCodeError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiCodeException.class)
    public ResponseEntity<ApiCodeError> handleApiCodeException(final ApiCodeException ex) {
        final ApiCodeError apiCodeError = new ApiCodeError(
                ex.getErrorCode(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(apiCodeError, HttpStatus.BAD_REQUEST);
    }
}
