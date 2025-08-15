package com.loith.springhl.mapper;

import com.loith.springhl.dto.request.CategoryCreateDtoRequest;
import com.loith.springhl.dto.response.Category;
import com.loith.springhl.entity.CategoryEntity;
import java.time.Instant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    imports = {Instant.class})
public interface CategoryMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", expression = "java(Instant.now())")
  @Mapping(target = "updatedAt", expression = "java(Instant.now())")
  CategoryEntity toEntity(CategoryCreateDtoRequest createDtoRequest);

  Category toDto(CategoryEntity entity);
}
