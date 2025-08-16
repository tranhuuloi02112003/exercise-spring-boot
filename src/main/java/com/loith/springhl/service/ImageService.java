package com.loith.springhl.service;

import com.loith.springhl.entity.ImageEntity;
import com.loith.springhl.repository.ProductImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
  private final ProductImageRepository productImageRepository;

  public void saveImages(List<ImageEntity> images) {
    productImageRepository.saveAll(images);
  }
}
