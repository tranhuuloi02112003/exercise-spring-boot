package com.loith.springhl.dto.request;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateDtoRequest {
  public String name;
  public String description;
  public double price;
  public UUID categoryId;
  public List<String> fileUrls;
}
