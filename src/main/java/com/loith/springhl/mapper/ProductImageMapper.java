package com.loith.springhl.mapper;

import com.loith.springhl.entity.ImageEntity;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    imports = {Instant.class})
public interface ProductImageMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", expression = "java(Instant.now())")
  @Mapping(target = "updatedAt", expression = "java(Instant.now())")
  ImageEntity toEntity(String imageUrl, UUID productId);

  default List<ImageEntity> toEntityList(List<String> urls, UUID productId) {
    return urls.stream().map(url -> toEntity(url, productId)).toList();
  }
}
