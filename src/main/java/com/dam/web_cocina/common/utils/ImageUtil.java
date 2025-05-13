package com.dam.web_cocina.common.utils;

import com.dam.web_cocina.common.exceptions.ImageStorageException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.imaging.ImagingException;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageUtil {
    public static final String IMAGE_WEBP_VALUE = "image/webp";

    private static final Set<String> SUPPORTED_TYPES = Set.of(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            IMAGE_WEBP_VALUE
    );

    private static final long MAX_SIZE_BYTES = 2L * 1024 * 1024;

    public static void validateImage(MultipartFile file) {
        if (file.getSize() > MAX_SIZE_BYTES) {
            throw ImageStorageException.forTooLarge(MAX_SIZE_BYTES);
        }

        String contentType = file.getContentType();
        if (contentType == null || !SUPPORTED_TYPES.contains(contentType)) {
            throw ImageStorageException.forUnsupportedFormat(contentType);
        }
    }

    public static InputStream getSanitizedInputStream(MultipartFile file) {
        try {
            if (MediaType.IMAGE_JPEG_VALUE.equalsIgnoreCase(file.getContentType())) {
                return removeJpegMetadata(file);
            }
            return file.getInputStream();
        } catch (IOException e) {
            throw ImageStorageException.forSavingFailure(e.getMessage());
        }
    }

    private static InputStream removeJpegMetadata(MultipartFile file) throws IOException {
        try (
                InputStream inputStream = file.getInputStream();
                ByteArrayOutputStream output = new ByteArrayOutputStream()
        ) {
            new ExifRewriter().removeExifMetadata(inputStream, output);
            return new ByteArrayInputStream(output.toByteArray());
        } catch (ImagingException e) {
            return file.getInputStream();
        }
    }

    public static String getExtension(String contentType) {
        return switch (contentType) {
            case MediaType.IMAGE_PNG_VALUE -> ".png";
            case MediaType.IMAGE_JPEG_VALUE -> ".jpeg";
            case IMAGE_WEBP_VALUE -> ".webp";
            default -> throw ImageStorageException.forUnsupportedFormat(contentType);
        };
    }
}
