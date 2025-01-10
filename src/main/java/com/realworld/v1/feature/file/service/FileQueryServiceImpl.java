package com.realworld.v1.feature.file.service;


import com.realworld.v1.feature.file.domain.FileV1;
import com.realworld.v1.feature.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileQueryServiceImpl implements FileQueryService {
    private final FileRepository repository;

    @Override
    public FileV1 getFile(UUID id) {
        return repository.findById(id).get().toDomain();
    }
}
