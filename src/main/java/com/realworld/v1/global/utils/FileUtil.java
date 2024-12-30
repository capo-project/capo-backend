package com.realworld.v1.global.utils;

import com.realworld.v1.feature.file.domain.File;
import com.realworld.v1.feature.file.service.FileNameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URLConnection;


@Slf4j
@Component
public class FileUtil {

    public static File fileSetting(MultipartFile multipartFile) throws IOException {
        String contentType = URLConnection.guessContentTypeFromStream(new BufferedInputStream(multipartFile.getInputStream()));
        if (contentType == null) {
            contentType = multipartFile.getContentType();
        }

        FileNameGenerator fileNameGenerator = new FileNameGenerator();
        String fileName = fileNameGenerator.getMultipartFileName(multipartFile);

        String fileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        return File.builder()
                .name(fileName)
                .size(multipartFile.getSize())
                .extension(fileExtension)
                .contentType(contentType)
                .build();
    }

    public String osFilePathSeparation() {
        String osName = System.getProperty("os.name").toLowerCase();
        String rootPath = System.getProperty("user.dir");

        if (osName.contains("mac")) {
            log.info("mac :: ");
            return rootPath + java.io.File.separator + "src" + java.io.File.separator + "main" + java.io.File.separator + "resources";
        } else if (osName.contains("windows")) {
            log.info("windows :: ");
            return rootPath + java.io.File.separator + "src" + java.io.File.separator + "main" + java.io.File.separator + "resources";
        } else if (osName.contains("linux")) {
            return java.io.File.separator + "app" + java.io.File.separator;
        }

        return java.io.File.separator + "home";
    }
}

