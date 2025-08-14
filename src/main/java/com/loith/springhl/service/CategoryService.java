package com.loith.springhl.service;

import com.loith.springhl.dto.Category;
import com.loith.springhl.dto.CategoryCreateDtoRequest;
import com.loith.springhl.entity.CategoryEntity;
import com.loith.springhl.repository.CategoryRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;

  public Category createCategory(CategoryCreateDtoRequest createDtoRequest) {
    CategoryEntity categoryEntity = new CategoryEntity();

    categoryEntity.setName(createDtoRequest.getName());
    categoryEntity.setCreatedAt(Instant.now());
    categoryEntity.setUpdatedAt(Instant.now());

    return new Category(categoryRepository.save(categoryEntity));
  }
}
