package com.loith.springhl.service;

import com.loith.springhl.entity.ProductImageEntity;
import com.loith.springhl.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {
    private   final ProductImageRepository productImageRepository;

    public void saveImages(List<ProductImageEntity> images) {
         productImageRepository.saveAll(images);
    }

}
