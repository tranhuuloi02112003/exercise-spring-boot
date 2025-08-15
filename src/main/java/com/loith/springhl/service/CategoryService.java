package com.loith.springhl.service;

import com.loith.springhl.dto.request.CategoryCreateDtoRequest;
import com.loith.springhl.dto.response.Category;
import com.loith.springhl.entity.CategoryEntity;
import com.loith.springhl.mapper.CategoryMapper;
import com.loith.springhl.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  public Category createCategory(CategoryCreateDtoRequest createDtoRequest) {
    CategoryEntity categoryEntity = categoryMapper.toEntity(createDtoRequest);
    return categoryMapper.toDto(categoryRepository.save(categoryEntity));
  }
}
