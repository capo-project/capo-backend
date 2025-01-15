package com.realworld.feature.file.mock;

import com.realworld.feature.file.FileDetails;
import com.realworld.feature.file.FileMetaData;

import java.io.File;

public class MockFileData {

    public static final String TEMPORARY_DIRECTORY = "temporary";
    public static final String TEST_DIRECTORY = "test";
    public static final String TEST_URL = "https://example.com/file.jpg";

    public static final String TEST_UUID = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
    public static final char TEST_EXTENSION_SEPARATOR = '.';
    public static final String TEST_EXTENSION = "jpeg";
    public static final String TEST_CONTENT_TYPE = "image/jpeg";
    public static final long FILE_SIZE = 10 * 1024 * 1024;

    private static final String TEST_IMAGE_PATH = "src/test/resources/image/test-image.jpeg";
    public static final String TEST_FILE_NAME = TEST_UUID + TEST_EXTENSION_SEPARATOR + TEST_EXTENSION;

    public static final File testFile = new File(TEST_IMAGE_PATH);
    public static final FileDetails details = FileDetails.of(
            TEST_FILE_NAME,
            TEST_CONTENT_TYPE,
            testFile.length()
    );

    public static FileMetaData create(String destinationDirectory) {
        return FileMetaData.builder()
                .directory(destinationDirectory)
                .details(details)
                .build();
    }

}

