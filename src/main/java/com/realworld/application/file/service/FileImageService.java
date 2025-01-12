package com.realworld.application.file.service;

import com.realworld.feature.file.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileImageService {

    File saveResizedImage(String destinationDirectory, MultipartFile file, int width, int height);

    File saveImage(String destinationDirectory, MultipartFile file);

    String move(String sourcePath, String destinationDirectory);

    void delete(String sourcePath);

}
