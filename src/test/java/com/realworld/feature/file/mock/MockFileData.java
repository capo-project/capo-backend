package com.realworld.feature.file.mock;

import com.realworld.feature.file.entity.FileDetails;
import com.realworld.feature.file.entity.FileMetaData;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;

public class MockFileData {

    public static final String FILE_NAME = "test-image.jpeg";
    public static final String FILE_CONTENT_TYPE = "image/jpeg";
    public static final long FILE_SIZE = 10 * 1024 * 1024;

    public static final File testFile = new File("src/test/resources/image/test-image.jpeg");
    public static final FileDetails fileDetails = FileDetails.of(
            FILE_NAME,
            FILE_CONTENT_TYPE,
            testFile.length()
    );

    public static FileMetaData create(String targetDir) {
        return FileMetaData.builder()
                .directory(targetDir)
                .details(fileDetails)
                .build();
    }

    public static MockMultipartFile multipartFile = new MockMultipartFile(
            "file",
            FILE_NAME,
            FILE_CONTENT_TYPE,
            new byte[(int) FILE_SIZE]
    );

}
