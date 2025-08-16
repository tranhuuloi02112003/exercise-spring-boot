package com.loith.springhl.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  private Category category;
  private List<String> fileUrls;
}
