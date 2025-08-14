package com.loith.springhl.dto;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryCreateDtoRequest {
  private String name;
  private Instant createdAt;
  private Instant updatedAt;
}
