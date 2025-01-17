package com.realworld.infrastructure.cloud.aws;

import com.realworld.common.exception.custom.CustomFileExceptionHandler;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.feature.file.entity.FileMetaData;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;

import static com.realworld.feature.file.mock.MockFileData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled(
        "AWS S3 관련 테스트는 비용 발생 우려로 인해 현재는 비활성화합니다."
)
@Deprecated
@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AwsS3HandlerImplTest {

    private static final String BUCKET_NAME = "photocardsite";

    private InputStream inputStream;

    @Autowired
    private AwsS3Handler awsS3Handler;

    @BeforeEach
    void setUp() throws IOException {
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
        assertThatThrownBy(() -> awsS3Handler.move(nonExistentFileUrl, TEST_DIRECTORY))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_NOT_FOUND_ERROR.getMessage()
                );
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
        assertThatThrownBy(() -> awsS3Handler.delete(nonExistentFileUrl))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_NOT_FOUND_ERROR.getMessage()
                );
    }

}
