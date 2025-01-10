package com.realworld.application.file.port;

import com.realworld.application.file.dto.FileMetaData;

import java.io.InputStream;

public interface FileStorage {

    String save(FileMetaData metaData, InputStream stream);

    String move(String sourcePath, String targetDirectory);

    void delete(String sourcePath);

}
