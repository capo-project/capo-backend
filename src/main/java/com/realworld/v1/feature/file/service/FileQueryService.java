package com.realworld.feature.file.service;

import com.realworld.feature.file.domain.File;

import java.util.UUID;

public interface FileQueryService {
    File getFile(UUID id);
}
