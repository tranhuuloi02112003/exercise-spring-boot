package com.loith.springhl.mapper;

import com.loith.springhl.dto.request.ProductCreateDtoRequest;
import com.loith.springhl.dto.response.Product;
import com.loith.springhl.entity.ProductEntity;
import java.time.Instant;
import java.util.List;
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

  Product toDto(ProductEntity entity);

  default Product toDto(ProductEntity entity, List<String> fileUrls) {
    Product product = toDto(entity);
    product.setFileUrls(fileUrls);
    return product;
  }

  List<Product> toDto(List<ProductEntity> entities);
}
