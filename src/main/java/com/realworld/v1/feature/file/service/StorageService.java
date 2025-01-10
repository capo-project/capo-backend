package com.realworld.v1.feature.file.service;


import com.realworld.v1.feature.file.domain.FileV1;

import java.io.InputStream;

public interface StorageService {
    FileV1 upload(InputStream inputStream, String userId, FileV1 fileV1);

    void delete(String userId, String fileId);

    String getFile(String id);
}
