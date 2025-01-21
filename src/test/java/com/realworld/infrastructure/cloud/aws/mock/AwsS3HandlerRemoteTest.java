package com.realworld.infrastructure.cloud.aws.mock;

import com.amazonaws.services.s3.AmazonS3;
import com.realworld.common.exception.custom.CustomFileExceptionHandler;
import com.realworld.common.localstack.TestLocalStackConfig;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.feature.file.entity.FileMetaData;
import com.realworld.feature.file.mock.MockFileData;
import com.realworld.infrastructure.cloud.aws.AwsS3Handler;
import com.realworld.infrastructure.cloud.aws.AwsS3HandlerImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.io.*;

import static org.assertj.core.api.Assertions.*;

@Disabled(
        """
        이 테스트 코드는 외부 IaaS 방식과 Testcontainers 방식을 비교하기 위한 목적으로 작성되었습니다.
        현재는 사용하지 않으므로 비활성화되어 있습니다.
        테스트를 실행하려면 main 패키지에 있는 AwsS3Config 클래스를 비활성화한 후 진행하시기 바랍니다.
        """
)
@Deprecated
@ActiveProfiles("test")
@ContextConfiguration(classes = TestLocalStackConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AwsS3HandlerRemoteTest {

    private static final String TEST_DIRECTORY = "temporary";

    @Value("${localstack.s3.bucket}")
    private String bucket;

    @Value("${localstack.cloudfront}")
    private String cloudFrontBaseUri;

    @Autowired
    private AmazonS3 s3Client;

    private InputStream inputStream;
    private AwsS3Handler awsS3Handler;

    @BeforeEach
    void setUp() throws IOException {
        inputStream = new FileInputStream(MockFileData.testFile);
        awsS3Handler = new AwsS3HandlerImpl(cloudFrontBaseUri, bucket, s3Client);
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
        FileMetaData metaData = MockFileData.create(TEST_DIRECTORY);
        String savedFileUrl = awsS3Handler.save(metaData, inputStream);

        // when
        awsS3Handler.delete(savedFileUrl);

        // then
        assertThat(awsS3Handler.isFileExist(getBucketPath(TEST_DIRECTORY), metaData.getDetails().getName())).isFalse();
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
