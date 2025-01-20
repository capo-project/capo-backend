package com.realworld.common.localstack;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;

@Validated
@Profile("test")
@TestConfiguration
public class TestLocalStackConfig {

    private final String region;
    private final String bucket;
    private final String endpoint;
    private final String accessKey;
    private final String secretKey;

    public TestLocalStackConfig(
            @NotNull @Value("${localstack.region.static}") String region,
            @NotNull @Value("${localstack.s3.bucket}") String bucket,
            @NotNull @Value("${localstack.s3.endpoint}") String endpoint,
            @NotNull @Value("${localstack.credentials.access-key}") String accessKey,
            @NotNull @Value("${localstack.credentials.secret-key}") String secretKey
    ) {
        this.region = region;
        this.bucket = bucket;
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withPathStyleAccessEnabled(true)
                .build();

        if (!amazonS3.doesBucketExistV2(bucket)) {
            amazonS3.createBucket(bucket);
        }

        return amazonS3;
    }

}
