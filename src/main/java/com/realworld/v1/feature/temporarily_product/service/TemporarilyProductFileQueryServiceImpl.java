package com.realworld.v1.feature.temporarily_product.service;

import com.realworld.v1.feature.temporarily_product.domain.TemporarilyProductFile;
import com.realworld.v1.feature.temporarily_product.repository.TemporarilyProductFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemporarilyProductFileQueryServiceImpl implements TemporarilyProductFileQueryService {
    private final TemporarilyProductFileRepository repository;

    @Override
    public TemporarilyProductFile temporarilyProductFileDetails(String id) {
        return repository.getFileDetails(UUID.fromString(id));
    }
}
