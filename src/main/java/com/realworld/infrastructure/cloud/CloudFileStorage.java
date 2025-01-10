package com.realworld.infrastructure.cloud;

import com.realworld.application.file.port.FileStorage;
import com.realworld.application.file.dto.FileMetaData;
import com.realworld.infrastructure.cloud.aws.AwsS3Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class CloudFileStorage implements FileStorage {

    private final AwsS3Handler awsS3Handler;

    @Override
    public String save(FileMetaData metaData, InputStream stream) {
        return awsS3Handler.save(metaData, stream);
    }

    @Override
    public String move(String sourcePath, String targetDirectory) {
        return awsS3Handler.move(sourcePath, targetDirectory);
    }

    @Override
    public void delete(String sourcePath) {
        awsS3Handler.delete(sourcePath);
    }

}
