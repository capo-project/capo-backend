/*
 * AWS S3 관련 테스트는 실제 AWS를 호출하므로 비용이 발생할 수 있습니다.
 * CI/CD 환경에서 이러한 테스트를 실행하지 않도록 주석 처리했습니다.
 * 로컬 환경에서 테스트가 필요한 경우 주석을 해제하고 실행해주세요.
 */

/*
package com.realworld.infrastructure.cloud.aws;

import com.realworld.common.exception.CustomFileExceptionHandler;
import com.realworld.feature.file.FileMetaData;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;

import static com.realworld.feature.file.mock.MockFileData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        assertThat(result).isNotNull();
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
*/
