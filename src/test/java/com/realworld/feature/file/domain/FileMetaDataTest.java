package com.realworld.feature.file.domain;

import com.realworld.common.exception.custom.CustomFileExceptionHandler;
import com.realworld.common.holder.uuid.UUIDHolder;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.common.type.file.FileFormat;
import com.realworld.feature.file.entity.FileMetaData;
import com.realworld.feature.file.mock.MockFileData;
import com.realworld.infrastructure.image.ResizedImage;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileMetaDataTest {

    private static final String TEST_DIRECTORY = "temporary";
    private static final String TEST_EXTENSION = "jpeg";
    private static final String TEST_UUID = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
    private static final String TEST_FILE_NAME = TEST_UUID + FileFormat.IMAGE.getExtensionSeparator() + TEST_EXTENSION;

    @Test
    void 리사이즈된_이미지로_파일_메타데이터를_생성한다() {
        // Given
        final String targetDir = TEST_DIRECTORY;
        final ResizedImage resizedImage = mock(ResizedImage.class);
        when(resizedImage.getImageFormat()).thenReturn(TEST_EXTENSION);
        when(resizedImage.getSize()).thenReturn(MockFileData.FILE_SIZE);

        // When
        final FileMetaData result = FileMetaData.fromResizedImage(
                targetDir,
                resizedImage,
                () -> TEST_UUID
        );

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getDirectory()).isEqualTo(targetDir);
        assertThat(result.getDetails()).isNotNull();
        assertThat(result.getDetails().getName()).isEqualTo(TEST_FILE_NAME);
        assertThat(result.getDetails().getContentType()).isEqualTo(MockFileData.FILE_CONTENT_TYPE);
        assertThat(result.getDetails().getSize()).isEqualTo(MockFileData.FILE_SIZE);
    }

    @Test
    void 리사이즈된_이미지가_NULL이면_예외를_던진다() {
        // Given
        final String targetDir = TEST_DIRECTORY;
        final UUIDHolder uuidHolder = () -> TEST_UUID;
        final ResizedImage invalidImage = null;

        // When & Then
        assertThatThrownBy(() -> FileMetaData.fromResizedImage(targetDir, invalidImage, uuidHolder))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

    @Test
    void 저장_디렉토리가_NULL이면_리사이즈된_이미지로_예외를_던진다() {
        // Given
        final String emptyDir = null;
        final UUIDHolder uuidHolder = () -> TEST_UUID;
        final ResizedImage resizedImage = mock(ResizedImage.class);
        when(resizedImage.getImageFormat()).thenReturn(TEST_EXTENSION);
        when(resizedImage.getSize()).thenReturn(MockFileData.FILE_SIZE);

        // when
        assertThatThrownBy(() -> FileMetaData.fromResizedImage(emptyDir, resizedImage, uuidHolder))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

    @Test
    void UUIDHolder가_NULL이면_리사이즈된_이미지로_예외를_던진다() {
        // Given
        final String targetDir = TEST_DIRECTORY;
        final UUIDHolder uuidHolder = null;
        final ResizedImage resizedImage = mock(ResizedImage.class);
        when(resizedImage.getImageFormat()).thenReturn(TEST_EXTENSION);
        when(resizedImage.getSize()).thenReturn(MockFileData.FILE_SIZE);

        // When & Then
        assertThatThrownBy(() -> FileMetaData.fromResizedImage(targetDir, resizedImage, uuidHolder))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

    @Test
    void MultipartFile로_파일_메타데이터를_생성한다() {
        // Given
        final String targetDir = TEST_DIRECTORY;
        final MockMultipartFile multipartFile = MockFileData.multipartFile;

        // When
        final FileMetaData result = FileMetaData.fromMultipartFile(
                targetDir,
                multipartFile,
                () -> TEST_UUID
        );

        // Then
        final String originalFileName = FilenameUtils.getName(multipartFile.getOriginalFilename());
        final String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        assertThat(result).isNotNull();
        assertThat(result.getDirectory()).isEqualTo(targetDir);
        assertThat(result.getDetails()).isNotNull();
        assertThat(result.getDetails().getName()).isEqualTo(TEST_FILE_NAME);
        assertThat(result.getDetails().getContentType()).isEqualTo(MockFileData.FILE_CONTENT_TYPE);
        assertThat(result.getDetails().getSize()).isEqualTo(MockFileData.FILE_SIZE);

        assertThat(originalFileName).isEqualTo(multipartFile.getOriginalFilename());
        assertThat(extension).isEqualTo(TEST_EXTENSION);
    }

    @Test
    void MultipartFile이_NULL이면_예외를_던진다() {
        // Given
        final String targetDir = TEST_DIRECTORY;
        final UUIDHolder uuidHolder = () -> TEST_UUID;
        final MockMultipartFile invalidFile = null;

        // When & Then
        assertThatThrownBy(() -> FileMetaData.fromMultipartFile(targetDir, invalidFile, uuidHolder))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

    @Test
    void 저장_디렉토리가_NULL이면_MultiptargetDirartFile로_예외를_던진다() {
        // Given
        final String emptyDir = null;
        final MockMultipartFile multipartFile = MockFileData.multipartFile;
        final UUIDHolder uuidHolder = () -> TEST_UUID;

        final ResizedImage resizedImage = mock(ResizedImage.class);
        when(resizedImage.getImageFormat()).thenReturn(TEST_EXTENSION);
        when(resizedImage.getSize()).thenReturn(MockFileData.FILE_SIZE);

        // When & Then
        assertThatThrownBy(() -> FileMetaData.fromMultipartFile(emptyDir, multipartFile, uuidHolder))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

    @Test
    void UUIDHolder가_NULL_이면_MultipartFile로_예외를_던진다() {
        // Given
        final String emptyDir = TEST_DIRECTORY;
        final UUIDHolder uuidHolder = null;
        final MockMultipartFile multipartFile = MockFileData.multipartFile;

        // When & Then
        assertThatThrownBy(() -> FileMetaData.fromMultipartFile(emptyDir, multipartFile, uuidHolder))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

}