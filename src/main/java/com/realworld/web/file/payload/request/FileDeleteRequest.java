package com.realworld.web.file.payload.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FileDeleteRequest {

    private final List<String> urls;

    @JsonCreator
    public FileDeleteRequest(final List<String> urls) {
        this.urls = Collections.unmodifiableList(urls);
    }

}
