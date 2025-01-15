package com.realworld.infrastructure.cloud.aws;

import com.realworld.feature.file.entity.FileMetaData;

import java.io.InputStream;

public interface AwsS3Handler {

    String save(FileMetaData metaData, InputStream stream);

    boolean isFileExist(String sourcePath, String fileName);

    String move(String sourcePath, String targetDirectory);

    void delete(String sourcePath);

}
