package com.realworld.infrastructure.cloud.test.mock;

import com.amazonaws.services.s3.AmazonS3;
import com.realworld.common.exception.CustomFileExceptionHandler;
import com.realworld.feature.file.entity.FileMetaData;
import com.realworld.infrastructure.cloud.aws.AwsS3Handler;
import com.realworld.infrastructure.cloud.aws.AwsS3HandlerImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;

import static com.realworld.feature.file.mock.MockFileData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled(
        """
        외부 laaS 방식과 Testcontainers 방식을 비교하기 위한 테스트 코드이므로,
        현재는 사용하지 않아 비활성화합니다.
        """
)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AwsS3HandlerMockTest {

    @Value("${localstack.localfront}")
    private String mockCloudFrontBaseUri;

    private static final String BUCKET_NAME = "photocardsite";

    @Autowired
    private AmazonS3 s3Client;

    private AwsS3Handler awsS3Handler;
    private InputStream inputStream;

    @BeforeEach
    void setUp() throws IOException {
        awsS3Handler = new AwsS3HandlerImpl(mockCloudFrontBaseUri, BUCKET_NAME, s3Client);
        inputStream = new FileInputStream(testFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
    }

    private String getBucketPath(String directory) {
        return BUCKET_NAME + "/" + directory;
    }

    @Test
    void AWS_S3_파일_업로드() {
        // given
        FileMetaData metaData = create(TEMPORARY_DIRECTORY);

        // when
        String result = awsS3Handler.save(metaData, inputStream);

        // then
        assertThat(result).isNotNull();
        assertThat(awsS3Handler.isFileExist(getBucketPath(TEMPORARY_DIRECTORY), metaData.getDetails().getName())).isTrue();
    }

    @Test
    void AWS_S3_파일_조회() {
        // given
        FileMetaData metaData = create(TEMPORARY_DIRECTORY);
        awsS3Handler.save(metaData, inputStream);

        // when
        Boolean result = awsS3Handler.isFileExist(getBucketPath(TEMPORARY_DIRECTORY), metaData.getDetails().getName());

        // then
        assertThat(result).isTrue();
    }

    @Test
    void AWS_S3_파일_이동() {
        // given
        FileMetaData metaData = create(TEMPORARY_DIRECTORY);
        String savedFileUrl = awsS3Handler.save(metaData, inputStream);

        // when
        String result = awsS3Handler.move(savedFileUrl, TEST_DIRECTORY);

        // then
        assertThat(result).isNotNull();
        assertThat(awsS3Handler.isFileExist(getBucketPath(TEST_DIRECTORY), metaData.getDetails().getName())).isTrue();
    }

    @Test
    void AWS_S3_존재하지_않는_파일_이동_시_예외_발생() {
        //given
        String nonExistentFileUrl = "https://xxxxxxxxxxxxxx.cloudfront.net/test/test.jpeg";

        // when & then
        assertThatThrownBy(
                () -> awsS3Handler.move(nonExistentFileUrl, TEST_DIRECTORY)
        ).isInstanceOf(CustomFileExceptionHandler.class);
    }

    @Test
    void AWS_S3_파일_삭제() {
        // given
        FileMetaData metaData = create(TEMPORARY_DIRECTORY);
        String savedFileUrl = awsS3Handler.save(metaData, inputStream);

        // when
        awsS3Handler.delete(savedFileUrl);

        // then
        assertThat(awsS3Handler.isFileExist(getBucketPath(TEMPORARY_DIRECTORY), metaData.getDetails().getName())).isFalse();
    }

    @Test
    void AWS_S3_존재하지_않는_파일_삭제_시_예외_발생() {
        //given
        String nonExistentFileUrl = "https://xxxxxxxxxxxxxx.cloudfront.net/test/test.jpeg";

        // when & then
        assertThatThrownBy(
                () -> awsS3Handler.delete(nonExistentFileUrl)
        ).isInstanceOf(CustomFileExceptionHandler.class);
    }

}
