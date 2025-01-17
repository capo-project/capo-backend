package com.realworld.infrastructure.image;

import com.realworld.application.file.port.ImageResizer;
import com.realworld.common.exception.custom.CustomFileExceptionHandler;
import com.realworld.common.response.code.ErrorCode;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class ImageResizerHandlerImpl implements ImageResizer {

    public static final String IMAGE_FORMAT_JPEG = "jpeg";

    @Override
    public ResizedImage resize(int width, int height, BufferedImage bufferedImage) {
        BufferedImage resizedImage = new BufferedImage(
                width, height, BufferedImage.TYPE_3BYTE_BGR
        );

        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(bufferedImage, 0, 0, width, height, null);
        graphics.dispose();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(resizedImage, IMAGE_FORMAT_JPEG, outputStream);

            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            return ResizedImage.of(inputStream, IMAGE_FORMAT_JPEG, outputStream.size());
        } catch (IOException e) {
            throw new CustomFileExceptionHandler(ErrorCode.FILE_IMAGE_PROCESSING_ERROR);
        }
    }

}
