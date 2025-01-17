package com.realworld.application.file.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.realworld.application.file.port.ImageResizer;
import com.realworld.common.exception.custom.CustomFileExceptionHandler;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.feature.file.entity.File;
import com.realworld.infrastructure.cloud.CloudFileStorage;
import com.realworld.infrastructure.cloud.aws.AwsS3Handler;
import com.realworld.infrastructure.cloud.aws.AwsS3HandlerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileServiceImplTest {

    private static final DockerImageName LOCALSTACK_IMAGE_NAME = DockerImageName.parse("localstack/localstack:latest");
    private static final String localFrontBaseUri = "http://localhost:4566/";
    private static final String BUCKET_NAME = "photocardsite";

    @Container
    public static final LocalStackContainer localStack = new LocalStackContainer(LOCALSTACK_IMAGE_NAME).withServices(LocalStackContainer.Service.S3);

    @Autowired
    private ImageResizer imageResizer;

    private AmazonS3 s3Client;
    private AwsS3Handler awsS3Handler;
    private CloudFileStorage cloudFileStorage;
    private FileService fileService;

    @BeforeEach
    void setUp() {
        s3Client = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                localStack.getEndpoint().toString(),
                                localStack.getRegion()
                        )
                )
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(localStack.getAccessKey(), localStack.getSecretKey())
                        )
                )
                .build();

        s3Client.createBucket(BUCKET_NAME);
        awsS3Handler = new AwsS3HandlerImpl(localFrontBaseUri, BUCKET_NAME, s3Client);
        cloudFileStorage = new CloudFileStorage(awsS3Handler);
        fileService = new FileServiceImpl(imageResizer, cloudFileStorage);
    }

    @Test
    void 이미지를_리사이징_후_저장한다() {
        // Given
        String destinationDirectory = "test";
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "test-image.jpeg",
                "image/jpeg",
                new byte[] {1, 2, 3, 4}
        );
        int width = 200;
        int height = 200;

        // When
        File result = fileService.saveResizedImage(destinationDirectory, multipartFile, width, height);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUrl()).contains(destinationDirectory);
    }

    @Test
    void 잘못된_이미지_파일을_저장하려고_하면_예외를_던진다() {
        // Given
        String destinationDirectory = "temporary";
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "test.txt",
                "test/plain",
                new byte[] {1, 2, 3, 4}
        );
        int width = 200;
        int height = 200;

        // When & Then
        assertThatThrownBy(() -> fileService.saveResizedImage(destinationDirectory, multipartFile, width, height))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.UNSUPPORTED_FILE_IMAGE_TYPE_ERROR.getMessage()
                );
    }

    @Test
    void 이미지를_저장한다() {
        // Given
        String destinationDirectory = "test";
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "test-image.jpeg",
                "image/jpeg",
                new byte[] {1, 2, 3, 4}
        );

        // When
        File result = fileService.saveImage(destinationDirectory, multipartFile);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUrl()).contains(destinationDirectory);
    }

}