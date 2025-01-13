package com.realworld.feature.file;

import lombok.Builder;
import lombok.Getter;

@Getter
public class File {

    private final FileDetails details;
    private final String url;

    @Builder
    private File(FileDetails details, String url) {
        this.details = details;
        this.url = url;
    }

    public static File create(FileMetaData metaData, String url) {
        return File.builder()
                .details(metaData.getDetails())
                .url(url)
                .build();
    }

}
