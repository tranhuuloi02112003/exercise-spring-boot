package com.loith.springhl.mapper;

import com.loith.springhl.dto.request.ProductCreateDtoRequest;
import com.loith.springhl.dto.response.Product;
import com.loith.springhl.entity.ProductEntity;
import java.time.Instant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    imports = {Instant.class})
public interface ProductMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", expression = "java(Instant.now())")
  @Mapping(target = "updatedAt", expression = "java(Instant.now())")
  ProductEntity toEntity(ProductCreateDtoRequest dto);

  @Mapping(target = "files", ignore = true)
  Product toDto(ProductEntity entity);
}
