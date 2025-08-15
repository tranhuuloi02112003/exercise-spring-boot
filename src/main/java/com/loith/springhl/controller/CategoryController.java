package com.loith.springhl.controller;

import com.loith.springhl.dto.request.CategoryCreateDtoRequest;
import com.loith.springhl.dto.response.Category;
import com.loith.springhl.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
  private final CategoryService categoryService;

  @PostMapping
  public Category createCategory(@RequestBody CategoryCreateDtoRequest categoryCreateDtoRequest) {
    return categoryService.createCategory(categoryCreateDtoRequest);
  }
}
