package com.dam.web_cocina.service;

import org.springframework.web.multipart.MultipartFile;

public interface IImageService {

    String saveImage(MultipartFile file);
}
