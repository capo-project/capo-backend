package com.realworld.feature.file.domain;

import com.realworld.common.exception.custom.CustomFileExceptionHandler;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.feature.file.entity.File;
import com.realworld.feature.file.entity.FileDetails;
import com.realworld.feature.file.mock.MockFileData;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class FileTest {

    private static final String TEST_URL = "https://example.com/test-image.jpg";

    @Test
    void 파일을_생성한다() {
        // Given
        FileDetails details = MockFileData.fileDetails;
        String testUrl = TEST_URL;

        // When
        File result = File.create(details, testUrl);

        assertThat(result).isNotNull();
        assertThat(result.getDetails()).isEqualTo(details);
        assertThat(result.getUrl()).isEqualTo(testUrl);
    }

    @Test
    void 파일을_생성할_때_상세_정보가_NULL이면_예외를_던진다() {
        // When & Then
        assertThatThrownBy(() -> File.create(null, TEST_URL))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

    @Test
    void 파일을_생성할_때_URL이_NULL이면_예외를_던진다() {
        // When & Then
        assertThatThrownBy(() -> File.create(MockFileData.fileDetails, null))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

}
