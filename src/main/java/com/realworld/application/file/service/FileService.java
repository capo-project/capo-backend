package com.realworld.application.file.service;

import com.realworld.feature.file.entity.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    File saveResizedImage(String targetDir, MultipartFile file, int width, int height);

    File saveImage(String targetDir, MultipartFile file);

    String move(String sourcePath, String targetDir);

    void delete(String sourcePath);

}
