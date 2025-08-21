package com.loith.springhl.entity;

import java.time.Instant;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  @Schema(description = "Unique product ID", example = "550e8400-e29b-41d4-a716-446655440000")
  private UUID id;

  @Column(name = "name")
  @Schema(description = "Product name", example = "iPhone 15 Pro")
  private String name;

  @Schema(description = "Product description", example = "Latest Apple smartphone with A17 chip")
  private String description;

  @Schema(description = "Product price", example = "1000000")
  private double price;

  @Column(name = "created_at")
  @Schema(description = "Timestamp when the product was created", example = "2024-08-15T10:15:30Z")
  private Instant createdAt;

  @Column(name = "updated_at")
  @Schema(description = "Timestamp when the product was last updated", example = "2024-08-20T12:00:00Z")
  private Instant updatedAt;

  @Column(name = "category_id")
  @Schema(description = "Category ID reference", example = "660e8400-e29b-41d4-a716-556655440111")
  private UUID categoryId;
}
