package com.dam.web_cocina.service;

import com.dam.web_cocina.common.exceptions.ImageStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements IImageService {

    private static final String UPLOAD_DIR = "uploads";

    @Value("${app.backend.url}")
    private String backendUrl;

    @Override
    public String saveImage(MultipartFile file) {
        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(filename);
            Files.write(filePath, file.getBytes());

            return backendUrl + "/uploads/" + filename;

        } catch (IOException e) {
            throw new ImageStorageException(e.getMessage());
        }
    }
}