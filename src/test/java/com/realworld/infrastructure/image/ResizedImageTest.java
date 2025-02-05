package com.realworld.infrastructure.image;

import com.realworld.common.exception.custom.CustomImageExceptionHandler;
import com.realworld.common.response.code.ErrorCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ResizedImageTest {

    private static final String TEST_IMAGE_PATH = "src/test/resources/image/test-image.jpeg";

    private File testFileImage;
    private InputStream inputStream;


    @BeforeEach
    void setUp() throws IOException {
        testFileImage = new File(TEST_IMAGE_PATH);
        inputStream = new FileInputStream(testFileImage);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (!Objects.isNull(inputStream)) {
            inputStream.close();
        }
    }

    @Test
    void 리사이즈된_이미지를_생성한다() {
        // Given
        final String imageFormat = "jpeg";
        final long size = testFileImage.length();

        // When
        final ResizedImage result = ResizedImage.of(inputStream, imageFormat, size);

        // Then
        assertThat(result.getInputStream()).isEqualTo(inputStream);
        assertThat(result.getImageFormat()).isEqualTo(imageFormat);
        assertThat(result.getSize()).isEqualTo(size);
    }

    @Test
    void 리사이즈된_이미지_반환_시_InputStream이_닫힌다() throws Exception {
        // Given
        final String imageFormat = "jpeg";
        final long size = testFileImage.length();
        final ResizedImage result = ResizedImage.of(inputStream, imageFormat, size);

        // When
        result.close();

        // Then
        assertThatThrownBy(
                () -> result.getInputStream().read()
        ).isInstanceOf(IOException.class);
    }

    @Test
    void 파일_생성_시_테스트_이미지_파일이_존재하지_않으면_예외를_던진다() {
        // Given
        final String imageFormat = "jpeg";
        final long size = testFileImage.length();

        // when & Then
        assertThatThrownBy(() -> ResizedImage.of(null, imageFormat, size))
                .isInstanceOf(CustomImageExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_IMAGE_PROCESSING_ERROR.getMessage()
                );
    }

    @Test
    void 파일_생성_시_이미지_포맷_형식이_NULL이면_예외를_던진다() {
        // Given
        String imageFormat = null;
        long size = testFileImage.length();

        // When & Then
        assertThatThrownBy(() -> ResizedImage.of(inputStream, imageFormat, size))
                .isInstanceOf(CustomImageExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_IMAGE_PROCESSING_ERROR.getMessage()
                );
    }

    @Test
    void 파일_생성_시_파일_사이즈가_0이면_예외를_던진다() {
        // Given
        String imageFormat = "jpeg";
        long size = 0;

        // when & Then
        assertThatThrownBy(() -> ResizedImage.of(inputStream, imageFormat, size))
                .isInstanceOf(CustomImageExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_IMAGE_PROCESSING_ERROR.getMessage()
                );
    }

}