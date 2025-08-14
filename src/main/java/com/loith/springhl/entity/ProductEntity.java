package com.loith.springhl.entity;

import java.time.Instant;
import java.util.UUID;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
  @Id
  @Column("id")
  private UUID id;

  @Column("name")
  private String name;

  private String description;

  private double price;

  //  @CreatedDate
  @Column("created_at")
  private Instant createdAt;

  @Column("updated_at")
  //  @LastModifiedDate
  private Instant updatedAt;

  @Column("category_id")
  private UUID categoryId;
}
