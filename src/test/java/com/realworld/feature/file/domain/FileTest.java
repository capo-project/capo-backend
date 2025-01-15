package com.realworld.feature.file.domain;

import com.realworld.common.exception.CustomFileExceptionHandler;
import com.realworld.feature.file.entity.File;
import com.realworld.feature.file.entity.FileDetails;
import com.realworld.feature.file.mock.MockFileData;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileTest {

    @Test
    void 파일을_생성한다() {
        // Given
        FileDetails details = MockFileData.details;
        String testUrl = MockFileData.TEST_URL;

        // When
        File result = File.create(details, testUrl);

        assertThat(result).isNotNull();
        assertThat(result.getDetails()).isEqualTo(details);
        assertThat(result.getUrl()).isEqualTo(testUrl);
    }

    @Test
    void 파일을_생성할_때_상세_정보가_NULL이면_예외를_던진다() {
        // When & Then
        assertThatThrownBy(
                () -> File.create(null, MockFileData.TEST_URL)
        ).isInstanceOf(CustomFileExceptionHandler.class);
    }

    @Test
    void 파일을_생성할_때_URL이_NULL이면_예외를_던진다() {
        // When & Then
        assertThatThrownBy(
                () -> File.create(MockFileData.details, null)
        ).isInstanceOf(CustomFileExceptionHandler.class);
    }

}
