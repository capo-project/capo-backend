package com.realworld.feature.file.controller;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FileResponse {
    private String id;

    private String originalFileName;

    private String contentType;

    private long size;

    private String path;

    private boolean hasThumbnail;

    private String thumbnailPath;
}
