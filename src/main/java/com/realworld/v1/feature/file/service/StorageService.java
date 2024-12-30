package com.realworld.v1.feature.file.service;


import com.realworld.v1.feature.file.domain.File;

import java.io.InputStream;

public interface StorageService {
    File upload(InputStream inputStream, String userId, File file);

    void delete(String userId, String fileId);

    String getFile(String id);
}
