package com.loith.springhl.entity;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("product_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {

  @Id private UUID id;

  @Column("product_id")
  private UUID productId;

  @Column("image_url")
  private String imageUrl;

  @Column("created_at")
  private Instant createdAt;

  @Column("updated_at")
  private Instant updatedAt;
}
