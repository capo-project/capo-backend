package com.realworld.feature.file.service;

import com.realworld.global.utils.UUIDGenerator;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Component
public class FileNameGenerator {

    public String getMultipartFileName(MultipartFile mpf) {
        String fileName = new String(Objects.requireNonNull(mpf.getOriginalFilename()).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        return FilenameUtils.getBaseName(fileName);
    }

    public UUID createFileId() {
        return UUIDGenerator.createUUIDV2();
    }
}
