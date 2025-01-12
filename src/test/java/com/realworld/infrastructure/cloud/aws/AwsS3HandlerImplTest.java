/*
package com.realworld.infrastructure.cloud.aws;

import com.realworld.feature.file.FileMetaData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AwsS3HandlerImplTest {

    private static final String BUCKET_NAME = "photocardsite";
    private static final String TEMPORARY_DIRECTORY = "temporary";
    private static final String TEST_DIRECTORY = "test";
    private static final String TEST_IMAGE_PATH = "src/test/resources/image/test-image.jpeg";

    private File testFile;
    private FileMetaData fileMetaData;
    private InputStream inputStream;
    private String fileUrl;

    @Autowired
    private AwsS3Handler awsS3Handler;

    @BeforeEach
    void setUp() throws IOException {
        testFile = new File(TEST_IMAGE_PATH);
        inputStream = new FileInputStream(testFile);

        fileMetaData = FileMetaData.builder()
                .targetDirectory(TEMPORARY_DIRECTORY)
                .name("test-image.jpeg")
                .contentType("image/jpeg")
                .size(testFile.length())
                .build();
    }

    @Test
    void AWS_S3_파일_업로드() {
        // given
        // when
        fileUrl = awsS3Handler.save(fileMetaData, inputStream);

        // then
        assertThat(fileUrl).isNotNull();
        assertThat(awsS3Handler.isFileExist(getBucketPath(TEMPORARY_DIRECTORY), fileMetaData.getName())).isTrue();
    }

    @Test
    void AWS_S3_파일_조회() {
        // given
        fileUrl = awsS3Handler.save(fileMetaData, inputStream);

        // when
        Boolean result = awsS3Handler.isFileExist(getBucketPath(TEMPORARY_DIRECTORY), fileMetaData.getName());

        // then
        assertThat(result).isNotNull();
        assertThat(result).isTrue();
    }

    @Test
    void AWS_S3_파일_이동() {
        // given
        // when
        fileUrl = awsS3Handler.save(fileMetaData, inputStream);

        // when
        awsS3Handler.move(fileUrl, TEST_DIRECTORY);

        // then
        assertThat(awsS3Handler.isFileExist(getBucketPath(TEST_DIRECTORY), fileMetaData.getName())).isTrue();
    }

    @Test
    void AWS_S3_파일_삭제() {
        // given
        fileUrl = awsS3Handler.save(fileMetaData, inputStream);

        // when
        awsS3Handler.delete(fileUrl);

        // then
        assertThat(awsS3Handler.isFileExist(getBucketPath(TEMPORARY_DIRECTORY), fileMetaData.getName())).isFalse();
    }

    @AfterEach
    void tearDown() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
        awsS3Handler.delete(fileUrl);
    }

    private String getBucketPath(String directory) {
        return BUCKET_NAME + "/" + directory;
    }

}
*/
