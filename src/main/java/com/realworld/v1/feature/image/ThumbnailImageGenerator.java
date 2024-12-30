package com.realworld.feature.image;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
@RequiredArgsConstructor
public class ThumbnailImageGenerator {

    final private ResizeImageGenerator resizeImageGenerator;

    public static final String THUMBNAIL_IMAGE_EXTENSION = "jpg";
    public static final String THUMBNAIL_PREFIX = "thumb_";

    public BufferedImage thumbnailBufferedImage(BufferedImage bufferedImage) {
        BufferedImage afterImg = new BufferedImage(bufferedImage.getWidth(),
                bufferedImage.getHeight()
                , BufferedImage.TYPE_3BYTE_BGR);
        afterImg.createGraphics().drawImage(bufferedImage, 0, 0, null);

        // TODO: 이미지 비율 맞추기

        return resizeImageGenerator.generate(afterImg);
    }

    public String createThumbnailFileName(String originFileName) {
        return THUMBNAIL_PREFIX + originFileName + FilenameUtils.EXTENSION_SEPARATOR_STR + THUMBNAIL_IMAGE_EXTENSION;
    }
}