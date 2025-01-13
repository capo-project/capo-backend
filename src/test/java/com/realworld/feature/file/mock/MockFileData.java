package com.realworld.feature.file.mock;

import com.realworld.feature.file.FileDetails;
import com.realworld.feature.file.FileMetaData;

import java.io.File;

public class MockFileData {

    public static final String TEMPORARY_DIRECTORY = "temporary";
    public static final String TEST_DIRECTORY = "test";
    public static final String TEST_IMAGE_PATH = "src/test/resources/image/test-image.jpeg";

    public static File testFile = new File(TEST_IMAGE_PATH);

    public static final FileDetails details = FileDetails.of(
            "test-image.jpeg",
            "image/jpeg",
            testFile.length()
    );

    public static FileMetaData create(String destinationDirectory) {
        return FileMetaData.builder()
                .directory(destinationDirectory)
                .details(details)
                .build();
    }

}

