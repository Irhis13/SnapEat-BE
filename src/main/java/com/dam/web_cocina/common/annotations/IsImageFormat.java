package com.dam.web_cocina.common.annotations;

import com.dam.web_cocina.common.annotations.validators.IsImageFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {IsImageFormatValidator.class})
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsImageFormat {
    String message() default "file format not accepted";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
