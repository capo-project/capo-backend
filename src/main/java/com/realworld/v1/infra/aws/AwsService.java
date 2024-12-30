package com.realworld.v1.infra.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadS3Bucket(InputStream inputStream, String key, long fileSize, String contentType) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileSize);
        metadata.setContentType(contentType);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, inputStream, metadata);

        amazonS3.putObject(putObjectRequest);

        return amazonS3.getUrl(bucket, key).toString();
    }

    public String getFile(String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public void deleteS3Bucket(String key) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, key);
        amazonS3.deleteObject(request);
    }
}
