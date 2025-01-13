package com.realworld.v1.feature.file.service;


import com.realworld.v1.feature.file.domain.FileV1;

import java.util.UUID;

public interface FileQueryService {
    FileV1 getFile(UUID id);
}
