package com.realworld.infrastructure.cloud.aws;

import com.realworld.common.exception.custom.CustomFileExceptionHandler;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.feature.file.entity.FileMetaData;
import com.realworld.feature.file.mock.MockFileData;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;

import static org.assertj.core.api.Assertions.*;

@Disabled(
        """
        AWS S3 관련 테스트는 비용 발생 우려로 인해 비활성화되어 있습니다.
        로컬 환경에서 테스트할 경우, application-local.yml을 기준으로 실행해야 합니다.
        CI/CD 환경에서 활성화할 경우, application-dev.yml을 기준으로 변경 후 실행해야 합니다.
        """
)
@Deprecated
@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AwsS3HandlerImplTest {

    private static final String TEST_DIRECTORY = "temporary";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    private AwsS3Handler awsS3Handler;

    private InputStream inputStream;

    @BeforeEach
    void setUp() throws IOException {
        inputStream = new FileInputStream(MockFileData.testFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
    }

    @Test
    void 파일을_S3에_업로드하면_정상적으로_업로드된_URL을_반환한다() {
        // given
        final FileMetaData metaData = MockFileData.create(TEST_DIRECTORY);

        // when
        final String result = awsS3Handler.save(metaData, inputStream);

        // then
        assertThat(result).isNotNull();
        assertThat(awsS3Handler.isFileExist(getBucketPath(TEST_DIRECTORY), metaData.getDetails().getName())).isTrue();
    }

    @Test
    void S3에_업로드된_파일이_존재하는지_확인한다() {
        // given
        final FileMetaData metaData = MockFileData.create(TEST_DIRECTORY);
        awsS3Handler.save(metaData, inputStream);

        // when
        final boolean result = awsS3Handler.isFileExist(getBucketPath(TEST_DIRECTORY), metaData.getDetails().getName());

        // then
        assertThat(result).isTrue();
    }

    @Test
    void S3_파일을_다른_디렉토리로_이동하면_정상적으로_이동된다() {
        // given
        final FileMetaData metaData = MockFileData.create(TEST_DIRECTORY);
        final String savedFileUrl = awsS3Handler.save(metaData, inputStream);

        // when
        final String result = awsS3Handler.move(savedFileUrl, "test");

        // then
        assertThat(result).isNotNull();
        assertThat(awsS3Handler.isFileExist(getBucketPath("test"), metaData.getDetails().getName())).isTrue();
    }

    @Test
    void 존재하지_않는_S3_파일을_이동하려고_하면_예외를_발생시킨다() {
        // when & then
        assertThatThrownBy(() -> awsS3Handler.move("https://xxxxxxxxxxxxxx.cloudfront.net/test/test.jpeg", TEST_DIRECTORY))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_NOT_FOUND_ERROR.getMessage()
                );
    }

    @Test
    void S3에서_파일을_삭제하면_정상적으로_삭제된다() {
        // given
        final FileMetaData metaData = MockFileData.create(TEST_DIRECTORY);
        final String savedFileUrl = awsS3Handler.save(metaData, inputStream);

        // when
        awsS3Handler.delete(savedFileUrl);

        // then
        assertThat(awsS3Handler.isFileExist(getBucketPath(TEST_DIRECTORY), metaData.getDetails().getName())).isFalse();
    }

    @Test
    void 존재하지_않는_S3_파일을_삭제하려고_하면_예외를_발생시킨다() {
        // when & then
        assertThatThrownBy(() -> awsS3Handler.delete("https://xxxxxxxxxxxxxx.cloudfront.net/test/test.jpeg"))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_NOT_FOUND_ERROR.getMessage()
                );
    }

    private String getBucketPath(String directory) {
        return bucket + "/" + directory;
    }

}
