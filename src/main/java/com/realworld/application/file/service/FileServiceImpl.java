package com.realworld.application.file.service;

import com.realworld.application.file.port.FileStorage;
import com.realworld.application.file.port.ImageResizer;
import com.realworld.common.exception.custom.CustomFileExceptionHandler;
import com.realworld.common.exception.custom.CustomImageExceptionHandler;
import com.realworld.common.holder.uuid.UUIDHolderImpl;
import com.realworld.common.response.code.ErrorCode;
import com.realworld.common.type.file.FileFormat;
import com.realworld.feature.file.entity.File;
import com.realworld.feature.file.entity.FileMetaData;
import com.realworld.infrastructure.image.ResizedImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final ImageResizer imageResizer;
    private final FileStorage fileStorage;

    @Override
    public File saveResizedImage(final String targetDir, final MultipartFile file, final int width, final int height) {
        validateImageFileType(file);
        try (InputStream inputStream = file.getInputStream();
             ResizedImage resizedImage = imageResizer.resize(width, height, ImageIO.read(inputStream))
        ) {
            FileMetaData metaData = FileMetaData.fromResizedImage(targetDir, resizedImage, new UUIDHolderImpl());
            String url = fileStorage.save(metaData, resizedImage.getInputStream());
            return File.create(metaData.getDetails(), url);
        } catch (IOException e) {
            throw new CustomFileExceptionHandler(ErrorCode.FILE_PROCESSING_ERROR);
        } catch (Exception e) {
            throw new CustomImageExceptionHandler(ErrorCode.FILE_IMAGE_RESIZE_ERROR);
        }
    }

    @Override
    public File saveImage(final String targetDir, final MultipartFile file) {
        validateImageFileType(file);
        try (InputStream inputStream = file.getInputStream()) {
            FileMetaData metaData = FileMetaData.fromMultipartFile(targetDir, file, new UUIDHolderImpl());
            String url = fileStorage.save(metaData, inputStream);
            return File.create(metaData.getDetails(), url);
        } catch (IOException e) {
            throw new CustomFileExceptionHandler(ErrorCode.FILE_PROCESSING_ERROR);
        }
    }

    private void validateImageFileType(final MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith(FileFormat.IMAGE.getPrefix())) {
            throw new CustomFileExceptionHandler(ErrorCode.UNSUPPORTED_FILE_IMAGE_TYPE_ERROR);
        }
    }

    @Override
    public String move(final String sourcePath, final String targetDir) {
        return fileStorage.move(sourcePath, targetDir);
    }

    @Override
    public void delete(final String sourcePath) {
        fileStorage.delete(sourcePath);
    }

}
