package com.realworld.infrastructure.cloud.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.realworld.common.exception.CustomFileExceptionHandler;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.feature.file.FileMetaData;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.io.InputStream;

@Validated
@Component
public class AwsS3HandlerImpl implements AwsS3Handler {

    private final String cloudFrontBasePath;
    private final String bucketName;
    private final AmazonS3 s3Client;

    public AwsS3HandlerImpl(
            @NotNull @Value("${cloud.aws.cloudfront}") String cloudFrontBasePath,
            @NotNull @Value("${cloud.aws.s3.bucket}") String bucketName,
            AmazonS3 s3Client

    ) {
        this.cloudFrontBasePath = cloudFrontBasePath;
        this.bucketName = bucketName;
        this.s3Client = s3Client;
    }

    @Override
    public String save(FileMetaData metaData, InputStream stream) {
        String bucketPath = bucketName + File.separator + metaData.getDirectory();
        String fileName = metaData.getDetails().getName();
        ObjectMetadata metadata = getObjectMetadata(metaData.getDetails().getContentType(), metaData.getDetails().getSize());

        s3Client.putObject(
                new PutObjectRequest(
                        bucketPath,
                        fileName,
                        stream,
                        metadata
                ).withCannedAcl(CannedAccessControlList.Private)
        );

        return cloudFrontBasePath + metaData.getDirectory() + File.separator + metaData.getDetails().getName();
    }

    private ObjectMetadata getObjectMetadata(String contentType, long size) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(size);
        return metadata;
    }

    @Override
    public boolean isFileExist(String sourcePath, String fileName) {
        return s3Client.doesObjectExist(sourcePath, fileName);
    }

    @Override
    public String move(String sourcePath, String targetDirectory) {
        String fileName = extractFileName(sourcePath);
        String sourceDirectory = extractDirectoryName(sourcePath);

        String sourceFullPath = bucketName + File.separator + sourceDirectory;
        String targetFullPath = bucketName + File.separator + targetDirectory;

        if (!isFileExist(sourceFullPath, fileName)) {
            throw new CustomFileExceptionHandler(ExceptionResponseCode.FILE_NOT_FOUND_ERROR);
        }

        s3Client.copyObject(sourceFullPath, fileName, targetFullPath, fileName);

        return cloudFrontBasePath + targetDirectory + File.separator + fileName;
    }

    @Override
    public void delete(String sourcePath) {
        String fileName = extractFileName(sourcePath);
        String sourceDirectory = extractDirectoryName(sourcePath);
        String targetFullPath = bucketName + File.separator + sourceDirectory;

        if (isFileExist(targetFullPath, fileName)) {
            s3Client.deleteObject(targetFullPath, fileName);
        } else {
            throw new CustomFileExceptionHandler(ExceptionResponseCode.FILE_NOT_FOUND_ERROR);
        }
    }

    private String extractDirectoryName(String sourcePath) {
        int nextPathSlashIndex = sourcePath.indexOf('/', cloudFrontBasePath.length());
        return sourcePath.substring(cloudFrontBasePath.length(), nextPathSlashIndex);
    }

    private String extractFileName(String sourcePath) {
        int lastSlashIndex = sourcePath.lastIndexOf('/');
        return sourcePath.substring(lastSlashIndex + 1);
    }

}
