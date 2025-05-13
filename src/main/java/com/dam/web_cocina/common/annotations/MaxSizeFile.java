package com.dam.web_cocina.common.annotations;

import com.dam.web_cocina.common.annotations.validators.MaxSizeFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {MaxSizeFileValidator.class})
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxSizeFile {
    String message() default "Request file is too large";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int size() default 100;
}
