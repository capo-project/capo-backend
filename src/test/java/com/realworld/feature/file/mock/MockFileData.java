package com.realworld.feature.file.mock;

import com.realworld.feature.file.entity.FileDetails;
import com.realworld.feature.file.entity.FileMetaData;
import org.springframework.mock.web.MockMultipartFile;

public class MockFileData {

    private static final String FILE_FORM_NAME = "file";

    public static final String FILE_NAME = "test-image.jpeg";
    public static final String FILE_CONTENT_TYPE = "image/jpeg";
    public static final long FILE_SIZE = 10 * 1024 * 1024;

    public static MockMultipartFile multipartFile = new MockMultipartFile(
            FILE_FORM_NAME,
            FILE_NAME,
            FILE_CONTENT_TYPE,
            new byte[(int) FILE_SIZE]
    );

    public static final FileDetails fileDetails = FileDetails.of(
            FILE_NAME,
            FILE_CONTENT_TYPE,
            FILE_SIZE
    );

    public static FileMetaData create(String destinationDirectory) {
        return FileMetaData.builder()
                .directory(destinationDirectory)
                .details(fileDetails)
                .build();
    }

}
