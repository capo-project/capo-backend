package com.realworld.infrastructure.image;

import com.realworld.application.file.port.ImageResizer;
import com.realworld.common.exception.custom.CustomFileExceptionHandler;
import com.realworld.common.response.code.ErrorCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImageResizerHandlerImplTest {

    private static final String TEST_IMAGE_PATH = "src/test/resources/image/test-image.jpeg";

    private InputStream inputStream;

    private ImageResizer imageResizerHandler;

    private MockedStatic<ImageIO> imageIOMockStatic;

    @BeforeEach
    void setUp() throws Exception {
        File testFileImage = new File(TEST_IMAGE_PATH);
        inputStream = new FileInputStream(testFileImage);
        imageResizerHandler = new ImageResizerHandlerImpl();
    }

    @AfterEach
    void tearDown() throws Exception {
        if (!Objects.isNull(inputStream)) {
            inputStream.close();
        }

        if (!Objects.isNull(imageIOMockStatic)) {
            imageIOMockStatic.close();
        }

        imageResizerHandler = null;
    }

    @Test
    void 이미지_리사이징이_정상적으로_수행된다() throws Exception {
        // Given
        BufferedImage originalImage = ImageIO.read(inputStream);
        int width = 200;
        int height = 200;

        // When
        ResizedImage result = imageResizerHandler.resize(width, height, originalImage);

        // Then
        assertThat(result.getImageFormat()).isEqualTo(ImageResizerHandlerImpl.IMAGE_FORMAT_JPEG);
        assertThat(result.getSize()).isGreaterThan(0);

        // Close Resource
        result.close();
    }

    @Test
    void 이미지_리사이징_중_IO예외가_발생하면_예외를_던진다() throws Exception {
        // Given
        BufferedImage originalImage = ImageIO.read(inputStream);
        int width = 200;
        int height = 200;

        imageIOMockStatic = Mockito.mockStatic(ImageIO.class);
        imageIOMockStatic.when(() -> ImageIO.write(
                Mockito.any(BufferedImage.class),
                Mockito.eq(ImageResizerHandlerImpl.IMAGE_FORMAT_JPEG),
                Mockito.any(ByteArrayOutputStream.class)
        )).thenThrow(new IOException());

        // When & Then
        assertThatThrownBy(() -> imageResizerHandler.resize(width, height, originalImage))
                .isInstanceOf(CustomFileExceptionHandler.class)
                .hasMessageContaining(
                        ErrorCode.FILE_IMAGE_PROCESSING_ERROR.getMessage()
                );
    }

}