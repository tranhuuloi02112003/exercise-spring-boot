package com.loith.springhl.service;

import com.loith.springhl.dto.Product;
import com.loith.springhl.dto.ProductCreateDtoRequest;
import com.loith.springhl.dto.ProductUpdateDtoRequest;
import com.loith.springhl.entity.ProductEntity;
import com.loith.springhl.entity.ProductImageEntity;
import com.loith.springhl.repository.ProductRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final UploadFileService uploadFileService;
  private final ProductImageService productImageService;

  public Product createProduct(ProductCreateDtoRequest productCreateDto) {
    ProductEntity productEntity = new ProductEntity();
    productEntity.setName(productCreateDto.getName());
    productEntity.setDescription(productCreateDto.getDescription());
    productEntity.setPrice(productCreateDto.getPrice());
    productEntity.setCreatedAt(Instant.now());
    productEntity.setUpdatedAt(Instant.now());
    productEntity.setCategoryId(productCreateDto.getCategoryId());

    ProductEntity savedProduct = productRepository.save(productEntity);

    List<String> fileUrls = uploadFileService.uploadMultipleFiles(productCreateDto.getFiles());

    List<ProductImageEntity> imageEntities = new ArrayList<>();
    for (String url : fileUrls) {
      ProductImageEntity imageEntity = new ProductImageEntity();
      imageEntity.setProductId(savedProduct.getId());
      imageEntity.setImageUrl(url);
      imageEntity.setCreatedAt(Instant.now());
      imageEntity.setUpdatedAt(Instant.now());
      imageEntities.add(imageEntity);
    }

    productImageService.saveImages(imageEntities);

    Product product = new Product(savedProduct);
    product.setFiles(fileUrls);

    return product;
  }

  public List<Product> getAll() {
    List<ProductEntity> productEntities = Streamable.of(productRepository.findAll()).toList();
    return productEntities.stream().map(Product::new).toList();
  }

  public Product getById(UUID id) {
    Optional<ProductEntity> productEntity = productRepository.findById(id);
    return productEntity
        .map(Product::new)
        .orElseThrow(() -> new IllegalArgumentException("Product not found"));
  }

  public void deleteById(UUID id) {
    getById(id);
    productRepository.deleteById(id);
  }

  public Product updateProduct(UUID uuid, ProductUpdateDtoRequest productUpdateDtoRequest) {
    Product product = getById(uuid);

    ProductEntity productEntity = new ProductEntity();

    productEntity.setId(uuid);

    productEntity.setName(
        productUpdateDtoRequest.getName() == null
            ? product.getName()
            : productUpdateDtoRequest.getName());

    productEntity.setDescription(
        productUpdateDtoRequest.getDescription() == null
            ? product.getDescription()
            : productUpdateDtoRequest.getDescription());

    productEntity.setPrice(
        productUpdateDtoRequest.getPrice() == 0
            ? product.getPrice()
            : productUpdateDtoRequest.getPrice());

    productEntity.setCreatedAt(product.getCreatedAt());

    productEntity.setUpdatedAt(Instant.now());

    return new Product(productRepository.save(productEntity));
  }
}
