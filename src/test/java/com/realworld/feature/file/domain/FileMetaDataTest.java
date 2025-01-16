package com.realworld.feature.file.domain;

import com.realworld.common.exception.CustomFileExceptionHandler;
import com.realworld.common.holder.uuid.UUIDHolder;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.feature.file.entity.FileMetaData;
import com.realworld.feature.file.mock.MockFileData;
import com.realworld.infrastructure.image.ResizedImage;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileMetaDataTest {

    @Test
    void 리사이즈된_이미지로_파일_메타데이터를_생성한다() {
        // Given
        String destinationDirectory = MockFileData.TEST_DIRECTORY;
        ResizedImage resizedImage = mock(ResizedImage.class);
        when(resizedImage.getImageFormat()).thenReturn(MockFileData.TEST_EXTENSION);
        when(resizedImage.getSize()).thenReturn(MockFileData.FILE_SIZE);

        // When
        FileMetaData result = FileMetaData.fromResizedImage(
                destinationDirectory,
                resizedImage,
                () -> MockFileData.TEST_UUID
        );

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getDirectory()).isEqualTo(destinationDirectory);
        assertThat(result.getDetails()).isNotNull();
        assertThat(result.getDetails().getName()).isEqualTo(MockFileData.TEST_FILE_NAME);
        assertThat(result.getDetails().getContentType()).isEqualTo(MockFileData.TEST_CONTENT_TYPE);
        assertThat(result.getDetails().getSize()).isEqualTo(MockFileData.FILE_SIZE);
    }

    @Test
    void 리사이즈된_이미지가_NULL이면_예외를_던진다() {
        // Given
        String destinationDirectory = MockFileData.TEST_DIRECTORY;
        UUIDHolder uuidHolder = () -> MockFileData.TEST_UUID;
        ResizedImage invalidImage = null;

        // When & Then
        assertThatThrownBy(() -> FileMetaData.fromResizedImage(destinationDirectory, invalidImage, uuidHolder))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ExceptionResponseCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

    @Test
    void 저장_디렉토리가_NULL이면_리사이즈된_이미지로_예외를_던진다() {
        // Given
        String emptyDirectory = null;
        UUIDHolder uuidHolder = () -> MockFileData.TEST_UUID;
        ResizedImage resizedImage = mock(ResizedImage.class);
        when(resizedImage.getImageFormat()).thenReturn(MockFileData.TEST_EXTENSION);
        when(resizedImage.getSize()).thenReturn(MockFileData.FILE_SIZE);

        // when
        assertThatThrownBy(() -> FileMetaData.fromResizedImage(emptyDirectory, resizedImage, uuidHolder))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ExceptionResponseCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

    @Test
    void UUIDHolder가_NULL이면_리사이즈된_이미지로_예외를_던진다() {
        // Given
        String destinationDirectory = MockFileData.TEST_DIRECTORY;
        UUIDHolder uuidHolder = null;
        ResizedImage resizedImage = mock(ResizedImage.class);
        when(resizedImage.getImageFormat()).thenReturn(MockFileData.TEST_EXTENSION);
        when(resizedImage.getSize()).thenReturn(MockFileData.FILE_SIZE);

        // When & Then
        assertThatThrownBy(() -> FileMetaData.fromResizedImage(destinationDirectory, resizedImage, uuidHolder))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ExceptionResponseCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

    @Test
    void MultipartFile로_파일_메타데이터를_생성한다() {
        // Given
        String destinationDirectory = MockFileData.TEST_DIRECTORY;
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "original-file.jpeg",
                "image/jpeg",
                new byte[(int) MockFileData.FILE_SIZE]
        );

        // When
        FileMetaData result = FileMetaData.fromMultipartFile(
                destinationDirectory,
                multipartFile,
                () -> MockFileData.TEST_UUID
        );

        // Then
        String originalFileName = FilenameUtils.getName(multipartFile.getOriginalFilename());
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        assertThat(result).isNotNull();
        assertThat(result.getDirectory()).isEqualTo(destinationDirectory);
        assertThat(result.getDetails()).isNotNull();
        assertThat(result.getDetails().getName()).isEqualTo(MockFileData.TEST_FILE_NAME);
        assertThat(result.getDetails().getContentType()).isEqualTo(MockFileData.TEST_CONTENT_TYPE);
        assertThat(result.getDetails().getSize()).isEqualTo(MockFileData.FILE_SIZE);

        assertThat(originalFileName).isEqualTo(multipartFile.getOriginalFilename());
        assertThat(extension).isEqualTo(MockFileData.TEST_EXTENSION);
    }

    @Test
    void MultipartFile이_NULL이면_예외를_던진다() {
        // Given
        String destinationDirectory = MockFileData.TEST_DIRECTORY;
        UUIDHolder uuidHolder = () -> MockFileData.TEST_UUID;
        MockMultipartFile invalidFile = null;

        // When & Then
        assertThatThrownBy(() -> FileMetaData.fromMultipartFile(destinationDirectory, invalidFile, uuidHolder))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ExceptionResponseCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

    @Test
    void 저장_디렉토리가_NULL이면_MultipartFile로_예외를_던진다() {
        // Given
        String emptyDirectory = null;
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "original-file.jpeg",
                "image/jpeg",
                new byte[(int) MockFileData.FILE_SIZE]
        );
        UUIDHolder uuidHolder = () -> MockFileData.TEST_UUID;

        ResizedImage resizedImage = mock(ResizedImage.class);
        when(resizedImage.getImageFormat()).thenReturn(MockFileData.TEST_EXTENSION);
        when(resizedImage.getSize()).thenReturn(MockFileData.FILE_SIZE);

        // When & Then
        assertThatThrownBy(() -> FileMetaData.fromMultipartFile(emptyDirectory, multipartFile, uuidHolder))
                .isInstanceOf(CustomFileExceptionHandler.class);
    }

    @Test
    void UUIDHolder가_NULL_이면_MultipartFile로_예외를_던진다() {
        // Given
        String emptyDirectory = MockFileData.TEST_DIRECTORY;
        UUIDHolder uuidHolder = null;
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "original-file.jpeg",
                "image/jpeg",
                new byte[(int) MockFileData.FILE_SIZE]
        );

        // When & Then
        assertThatThrownBy(() -> FileMetaData.fromMultipartFile(emptyDirectory, multipartFile, uuidHolder))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ExceptionResponseCode.FILE_PROCESSING_ERROR.getMessage()
                );
    }

}