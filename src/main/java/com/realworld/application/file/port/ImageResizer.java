package com.realworld.application.file.port;

import com.realworld.infrastructure.image.ResizedImage;

import java.awt.image.BufferedImage;

public interface ImageResizer {

    ResizedImage resize(int width, int height, BufferedImage bufferedImage);

}
