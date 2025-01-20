package com.realworld.infrastructure.cloud.aws.mock.testcontainers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.realworld.common.exception.custom.CustomFileExceptionHandler;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.feature.file.entity.FileMetaData;
import com.realworld.feature.file.mock.MockFileData;
import com.realworld.infrastructure.cloud.aws.AwsS3Handler;
import com.realworld.infrastructure.cloud.aws.AwsS3HandlerImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.*;

import static org.assertj.core.api.Assertions.*;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AwsS3HandlerTestContainersTest {

    private static final DockerImageName LOCALSTACK_IMAGE_NAME = DockerImageName.parse("localstack/localstack:latest");

    private static final String LOCALSTACK_URI = "http://localhost:4566/";
    private static final String TEST_DIRECTORY = "temporary";

    @Container
    public static final LocalStackContainer localStack = new LocalStackContainer(LOCALSTACK_IMAGE_NAME)
            .withServices(Service.S3);

    @Value("${localstack.s3.bucket}")
    private String bucket;

    private AmazonS3 s3Client;
    private AwsS3Handler awsS3Handler;

    private InputStream inputStream;

    @BeforeEach
    void setUp() throws IOException {
        inputStream = new FileInputStream(MockFileData.testFile);

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

        s3Client.createBucket(bucket);

        awsS3Handler = new AwsS3HandlerImpl(LOCALSTACK_URI, bucket, s3Client);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
    }

    private String getBucketPath(String directory) {
        return bucket + "/" + directory;
    }

    @Test
    void 파일을_S3에_업로드하면_정상적으로_업로드된_URL을_반환한다() {
        // given
        FileMetaData metaData = MockFileData.create(TEST_DIRECTORY);

        // when
        String result = awsS3Handler.save(metaData, inputStream);

        // then
        assertThat(result).isNotNull();
        assertThat(awsS3Handler.isFileExist(getBucketPath(TEST_DIRECTORY), metaData.getDetails().getName())).isTrue();
    }

    @Test
    void S3에_업로드된_파일이_존재하는지_확인한다() {
        // given
        FileMetaData metaData = MockFileData.create(TEST_DIRECTORY);
        awsS3Handler.save(metaData, inputStream);

        // when
        Boolean result = awsS3Handler.isFileExist(getBucketPath(TEST_DIRECTORY), metaData.getDetails().getName());

        // then
        assertThat(result).isTrue();
    }

    @Test
    void S3_파일을_다른_디렉토리로_이동하면_정상적으로_이동된다() {
        // given
        FileMetaData metaData = MockFileData.create(TEST_DIRECTORY);
        String savedFileUrl = awsS3Handler.save(metaData, inputStream);

        // when
        String result = awsS3Handler.move(savedFileUrl, "test");

        // then
        assertThat(result).isNotNull();
        assertThat(awsS3Handler.isFileExist(getBucketPath("test"), metaData.getDetails().getName())).isTrue();
    }

    @Test
    void 존재하지_않는_S3_파일을_이동하려고_하면_예외를_발생시킨다() {
        //given
        String nonExistentFileUrl = "https://xxxxxxxxxxxxxx.cloudfront.net/test/test.jpeg";

        // when & then
        assertThatThrownBy(() -> awsS3Handler.move(nonExistentFileUrl, TEST_DIRECTORY))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_NOT_FOUND_ERROR.getMessage()
                );
    }

    @Test
    void S3에서_파일을_삭제하면_정상적으로_삭제된다() {
        // given
        FileMetaData metaData = MockFileData.create(TEST_DIRECTORY);
        String savedFileUrl = awsS3Handler.save(metaData, inputStream);

        // when
        awsS3Handler.delete(savedFileUrl);

        // then
        assertThat(awsS3Handler.isFileExist(getBucketPath(TEST_DIRECTORY), metaData.getDetails().getName())).isFalse();
    }

    @Test
    void 존재하지_않는_S3_파일을_삭제하려고_하면_예외를_발생시킨다() {
        //given
        String nonExistentFileUrl = "https://xxxxxxxxxxxxxx.cloudfront.net/test/test.jpeg";

        // when & then
        assertThatThrownBy(() -> awsS3Handler.delete(nonExistentFileUrl))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_NOT_FOUND_ERROR.getMessage()
                );
    }

}
