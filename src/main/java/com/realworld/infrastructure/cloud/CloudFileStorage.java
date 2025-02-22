package com.realworld.infrastructure.cloud;

import com.realworld.application.file.port.FileStorage;
import com.realworld.feature.file.entity.FileMetaData;
import com.realworld.infrastructure.cloud.aws.AwsS3Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class CloudFileStorage implements FileStorage {

    private final AwsS3Handler awsS3Handler;

    @Override
    public String save(final FileMetaData metaData, final InputStream stream) {
        return awsS3Handler.save(metaData, stream);
    }

    @Override
    public String move(final String sourcePath, final String destinationPath) {
        return awsS3Handler.move(sourcePath, destinationPath);
    }

    @Override
    public void delete(final String sourcePath) {
        awsS3Handler.delete(sourcePath);
    }

}
