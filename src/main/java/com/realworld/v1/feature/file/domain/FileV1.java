package com.realworld.v1.feature.file.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.realworld.v1.feature.file.controller.FileResponseV1;
import com.realworld.v1.feature.file.entity.FileJpaEntity;
import com.realworld.v1.feature.image.ThumbnailImageGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.io.FilenameUtils;

import java.util.UUID;

@Getter
@Builder
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileV1 {

    private UUID id;

    private String name;

    private String contentType;
    private String extension;

    private long size;

    private String path;

    private boolean hasThumbnail;
    private String thumbnailPath;

    public void updateHasThumbnail(boolean value) {
        this.hasThumbnail = value;
    }

    public void updatePath(String path) {
        this.path = path;
    }

    public void updateSize(int size) {
        this.size = size;
    }

    public String getPath() {
        return "https://photocard.site/api/v1/file/" + getId();
    }

    public String getThumbnailPath() {
        //  return isHasThumbnail() ? (StringUtils.substringBefore(this.path, this.id.toString())
        //      + ThumbnailImageGenerator.THUMBNAIL_PREFIX + this.id) : "";

        return isHasThumbnail() ? "https://photocard.site/api/v1/file/" + ThumbnailImageGenerator.THUMBNAIL_PREFIX + getId() : "";
    }

    public void updateId(UUID id) {
        this.id = id;
    }

    public FileJpaEntity toEntity() {
        return FileJpaEntity.builder()
                .id(getId())
                .name(getName())
                .path(getPath())
                .extension(getExtension())
                .size(getSize())
                .hasThumbnail(isHasThumbnail())
                .build();
    }

    public FileResponseV1 toResponse() {
        return FileResponseV1.builder()
                .id(String.valueOf(getId()))
                .originalFileName(getName() + FilenameUtils.EXTENSION_SEPARATOR_STR + getExtension())
                .contentType(getContentType())
                .size(getSize())
                .path(getPath())
                .hasThumbnail(isHasThumbnail())
                .thumbnailPath(getThumbnailPath())
                .build();
    }
}
