package com.loith.springhl.dto.response;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

  private UUID id;
  private String name;
  private Instant createdAt;
  private Instant updatedAt;
}
