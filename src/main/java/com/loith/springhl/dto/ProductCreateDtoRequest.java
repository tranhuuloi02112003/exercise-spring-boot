package com.loith.springhl.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductCreateDtoRequest {
  public String name;
  public String description;
  public double price;
  public UUID categoryId;
  public List<MultipartFile> files;
  
}
