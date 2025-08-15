package com.loith.springhl.service;

import com.loith.springhl.entity.ProductImageEntity;
import com.loith.springhl.repository.ProductImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductImageService {
  private final ProductImageRepository productImageRepository;

  public void saveImages(List<ProductImageEntity> images) {
    productImageRepository.saveAll(images);
  }
}
