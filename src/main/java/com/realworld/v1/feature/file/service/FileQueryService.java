package com.realworld.v1.feature.file.service;


import com.realworld.v1.feature.file.domain.File;

import java.util.UUID;

public interface FileQueryService {
    File getFile(UUID id);
}
