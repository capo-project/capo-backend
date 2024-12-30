package com.realworld.feature.file.service;

import com.realworld.feature.file.domain.File;
import com.realworld.feature.file.repository.FileRepository;
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
    public File getFile(UUID id) {
        return repository.findById(id).get().toDomain();
    }
}
