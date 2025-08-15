package com.loith.springhl.service;

import com.loith.springhl.dto.request.ProductCreateDtoRequest;
import com.loith.springhl.dto.request.ProductUpdateDtoRequest;
import com.loith.springhl.dto.response.Product;
import com.loith.springhl.entity.ProductEntity;
import com.loith.springhl.entity.ProductImageEntity;
import com.loith.springhl.mapper.ProductImageMapper;
import com.loith.springhl.mapper.ProductMapper;
import com.loith.springhl.projection.ProductCategoryProjection;
import com.loith.springhl.repository.ProductRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final UploadFileService uploadFileService;
  private final ProductImageService productImageService;
  private final ProductMapper productMapper;
  private final ProductImageMapper productImageMapper;

  public Product createProduct(ProductCreateDtoRequest productCreateDto) {
    ProductEntity productEntity = productMapper.toEntity(productCreateDto);
    ProductEntity savedProduct = productRepository.save(productEntity);

    List<String> fileUrls = uploadFileService.uploadMultipleFiles(productCreateDto.getFiles());

    List<ProductImageEntity> productImageEntities =
        productImageMapper.toEntityList(fileUrls, savedProduct.getId());

    productImageService.saveImages(productImageEntities);

    Product product = productMapper.toDto(savedProduct);
    product.setFiles(fileUrls);

    return product;
  }

  public List<ProductCategoryProjection> getAll() {
    return productRepository.findAllProductCategoryInfo();
  }

//  public Product getById(UUID id) {
//    Optional<ProductEntity> productEntity = productRepository.findById(id);
//    return productEntity
//        .map(Product::new)
//        .orElseThrow(() -> new IllegalArgumentException("Product not found"));
//  }
//
//  public void deleteById(UUID id) {
//    getById(id);
//    productRepository.deleteById(id);
//  }
//
//  public Product updateProduct(UUID uuid, ProductUpdateDtoRequest productUpdateDtoRequest) {
//    Product product = getById(uuid);
//
//    ProductEntity productEntity = new ProductEntity();
//
//    productEntity.setId(uuid);
//
//    productEntity.setName(
//        productUpdateDtoRequest.getName() == null
//            ? product.getName()
//            : productUpdateDtoRequest.getName());
//
//    productEntity.setDescription(
//        productUpdateDtoRequest.getDescription() == null
//            ? product.getDescription()
//            : productUpdateDtoRequest.getDescription());
//
//    productEntity.setPrice(
//        productUpdateDtoRequest.getPrice() == 0
//            ? product.getPrice()
//            : productUpdateDtoRequest.getPrice());
//
//    productEntity.setCreatedAt(product.getCreatedAt());
//
//    productEntity.setUpdatedAt(Instant.now());
//
//    return new Product(productRepository.save(productEntity));
//  }
}
