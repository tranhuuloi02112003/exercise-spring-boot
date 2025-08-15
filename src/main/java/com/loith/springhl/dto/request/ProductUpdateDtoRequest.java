package com.loith.springhl.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateDtoRequest {
  public String name;
  public String description;
  public double price;
}
