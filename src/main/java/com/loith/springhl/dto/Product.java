package com.loith.springhl.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.loith.springhl.entity.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  private UUID id;
  private String name;
  private String description;
  private double price;
  private Instant createdAt;
  private Instant updatedAt;
  private UUID categoryId;
  private List<String> files;

  public Product(ProductEntity productEntity) {
    this.id = productEntity.getId();
    this.name = productEntity.getName();
    this.description = productEntity.getDescription();
    this.price = productEntity.getPrice();
    this.createdAt = productEntity.getCreatedAt();
    this.updatedAt = productEntity.getUpdatedAt();
    this.categoryId = productEntity.getCategoryId();
  }
}
