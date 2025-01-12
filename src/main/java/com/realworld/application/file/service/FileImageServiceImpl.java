package com.realworld.application.file.service;

import com.realworld.application.file.port.FileStorage;
import com.realworld.application.file.port.ImageResizer;
import com.realworld.common.exception.CustomFileExceptionHandler;
import com.realworld.common.exception.CustomImageExceptionHandler;
import com.realworld.common.holder.uuid.UUIDHolderImpl;
import com.realworld.common.response.code.ExceptionResponseCode;
import com.realworld.common.type.file.FileFormat;
import com.realworld.feature.file.File;
import com.realworld.common.type.file.FileMetaData;
import com.realworld.infrastructure.image.ResizedImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileImageServiceImpl implements FileImageService {

    private static final String TEMPORARY_DIRECTORY = "temporary";

    private final ImageResizer imageResizer;
    private final FileStorage fileStorage;

    @Override
    public File saveResizedImage(String destinationDirectory, MultipartFile file, int width, int height) {
        validateImageFileType(file);

        try (InputStream inputStream = file.getInputStream();
             ResizedImage resizedImage = imageResizer.resize(width, height, ImageIO.read(inputStream))
        ) {
            FileMetaData metaData = FileMetaData.create(
                    determineDestinationDirectory(destinationDirectory),
                    new UUIDHolderImpl(),
                    resizedImage
            );

            String url = fileStorage.save(metaData, resizedImage.getInputStream());

            return File.create(metaData, url);
        } catch (IOException e) {
            throw new CustomFileExceptionHandler(ExceptionResponseCode.FILE_PROCESSING_ERROR);
        } catch (Exception e) {
            throw new CustomImageExceptionHandler(ExceptionResponseCode.FILE_IMAGE_RESIZE_ERROR);
        }
    }

    @Override
    public File saveImage(String destinationDirectory, MultipartFile file) {
        validateImageFileType(file);

        try (InputStream inputStream = file.getInputStream()) {
            FileMetaData metaData = FileMetaData.create(
                    determineDestinationDirectory(destinationDirectory),
                    file,
                    new UUIDHolderImpl()
            );

            String url = fileStorage.save(metaData, inputStream);

            return File.create(metaData, url);
        } catch (IOException e) {
            throw new CustomFileExceptionHandler(ExceptionResponseCode.FILE_PROCESSING_ERROR);
        }
    }

    private void validateImageFileType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !FileFormat.IMAGE.getPrefix().contains(contentType)) {
            throw new CustomFileExceptionHandler(ExceptionResponseCode.UNSUPPORTED_FILE_IMAGE_TYPE_ERROR);
        }
    }

    private String determineDestinationDirectory(String destinationDirectory) {
        return destinationDirectory != null ? destinationDirectory : TEMPORARY_DIRECTORY;
    }

    @Override
    public String move(String sourcePath, String destinationDirectory) {
        return fileStorage.move(sourcePath, destinationDirectory);
    }

    @Override
    public void delete(String sourcePath) {
        fileStorage.delete(sourcePath);
    }

}
