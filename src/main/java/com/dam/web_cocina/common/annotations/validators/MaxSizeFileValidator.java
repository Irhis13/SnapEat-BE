package com.dam.web_cocina.common.annotations.validators;

import com.dam.web_cocina.common.annotations.MaxSizeFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class MaxSizeFileValidator implements ConstraintValidator<MaxSizeFile, MultipartFile> {

    private int maxSize;

    @Override
    public void initialize(MaxSizeFile constraintAnnotation) {
        this.maxSize = constraintAnnotation.size();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;
        return value.getSize() < maxSize * 1024L;
    }
}
