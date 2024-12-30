package com.realworld.feature.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.springframework.stereotype.Component;

@Component
public class ResizeImageGenerator {

    private int width;
    private int height;

    public ResizeImageGenerator() {
        this(200, 200);
    }

    public ResizeImageGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public BufferedImage generate(BufferedImage bufferedOriginalImage) {
        BufferedImage bufferedThumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D graphic = bufferedThumbnailImage.createGraphics();
        graphic.drawImage(bufferedOriginalImage, 0, 0, width, height, null);
        graphic.dispose();

        return bufferedThumbnailImage;
    }
}