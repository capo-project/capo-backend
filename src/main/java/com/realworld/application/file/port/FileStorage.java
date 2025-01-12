package com.realworld.application.file.port;

import com.realworld.feature.file.FileMetaData;

import java.io.InputStream;

public interface FileStorage {

    String save(FileMetaData metaData, InputStream stream);

    String move(String sourcePath, String destinationDirectory);

    void delete(String sourcePath);

}
