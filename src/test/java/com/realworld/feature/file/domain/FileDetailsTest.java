package com.realworld.feature.file.domain;

import com.realworld.common.exception.custom.CustomFileExceptionHandler;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.feature.file.entity.FileDetails;
import com.realworld.feature.file.mock.MockFileData;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileDetailsTest {

    @Test
    void 파일_상세_정보를_생성한다() {
        // Given
        String name = MockFileData.FILE_NAME;
        String contentType = MockFileData.FILE_CONTENT_TYPE;
        long size = 1024L;

        // When
        FileDetails result = FileDetails.of(name, contentType, size);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getContentType()).isEqualTo(contentType);
        assertThat(result.getSize()).isEqualTo(size);
    }

    @Test
    void 파일_이름이_NULL이면_예외를_던진다() {
        // Given
        String name = null;
        String contentType = MockFileData.FILE_CONTENT_TYPE;
        long size = 1024L;

        // When & Then
        assertThatThrownBy(() -> FileDetails.of(name, contentType, size))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

    @Test
    void 콘텐츠_타입이_NULL이면_예외를_던진다() {
        // Given
        String name = MockFileData.FILE_NAME;
        String contentType = null;
        long size = 1024L;

        // When & Then
        assertThatThrownBy(() -> FileDetails.of(name, contentType, size))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

    @Test
    void 파일_사이즈가_0이면_예외를_던진다() {
        // Given
        String name = MockFileData.FILE_NAME;
        String contentType = MockFileData.FILE_CONTENT_TYPE;
        long size = 0L;

        // When & Then
        assertThatThrownBy(() -> FileDetails.of(name, contentType, size))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

}