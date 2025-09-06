package com.loith.springhl.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateDtoRequest {
  @Size(max = 150, message = "name must be <= 150 characters")
  public String name;

  @Size(min = 20, message = "description must be > 20 characters")
  public String description;

  @PositiveOrZero(message = "price must be >= 0")
  public double price;

  @NotNull(message = "categoryId is required")
  public UUID categoryId;

  @Size(max = 10, message = "fileUrls must contain <= 10 items")
  public List<String> fileUrls;
}
