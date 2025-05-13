package com.dam.web_cocina.common.annotations.validators;

import com.dam.web_cocina.common.annotations.IsImageFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import static com.dam.web_cocina.common.utils.ImageUtil.IMAGE_WEBP_VALUE;

public class IsImageFormatValidator implements ConstraintValidator<IsImageFormat, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;

        final String contentType = value.getContentType();
        if (contentType == null) return false;

        return MediaType.IMAGE_JPEG_VALUE.equalsIgnoreCase(contentType)
                || MediaType.IMAGE_PNG_VALUE.equalsIgnoreCase(contentType)
                || IMAGE_WEBP_VALUE.equalsIgnoreCase(contentType);
    }
}
