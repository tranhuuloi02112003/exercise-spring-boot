package com.loith.springhl.service;

import com.google.common.collect.Lists;
import com.loith.springhl.dto.request.ProductCreateDtoRequest;
import com.loith.springhl.dto.response.Product;
import com.loith.springhl.entity.CategoryEntity;
import com.loith.springhl.entity.ImageEntity;
import com.loith.springhl.entity.ProductEntity;
import com.loith.springhl.mapper.CategoryMapper;
import com.loith.springhl.mapper.ProductImageMapper;
import com.loith.springhl.mapper.ProductMapper;
import com.loith.springhl.repository.CategoryRepository;
import com.loith.springhl.repository.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService productImageService;
    private final ProductMapper productMapper;
    private final ProductImageMapper productImageMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Transactional
    public Product createProduct(ProductCreateDtoRequest productCreateDto) {
        ProductEntity productEntity = productMapper.toEntity(productCreateDto);
        ProductEntity savedProduct = productRepository.save(productEntity);

        List<ImageEntity> productImageEntities =
                productImageMapper.toEntityList(productCreateDto.getFileUrls(), savedProduct.getId());

        productImageService.saveImages(productImageEntities);

        return productMapper.toDto(savedProduct, productCreateDto.getFileUrls());
    }

    public List<Product> getAll() {
        List<ProductEntity> productEntities = Lists.newArrayList(productRepository.findAll());

        List<Product> products = productMapper.toDto(productEntities);

        List<UUID> categoryIds = productEntities.stream().map(ProductEntity::getCategoryId).toList();

        mapCategoryToProducts(categoryIds, products);

        return products;
    }

    private void mapCategoryToProducts(List<UUID> categoryIds, List<Product> products) {
        Map<UUID, CategoryEntity> categoryMap = categoryRepository.findByIdIn(categoryIds).stream()
                .collect(Collectors.toMap(CategoryEntity::getId, e -> e));

        products.forEach(product -> {
            CategoryEntity categoryEntity = categoryMap.get(product.getCategoryId());
            if (categoryEntity != null) {
                product.setCategory(categoryMapper.toDto(categoryEntity));
            }
        });
    }


    //    public Product getById(UUID id) {
    //      Optional<ProductEntity> productEntity = productRepository.findById(id);
    //      return productEntity
    //          .map(Product::new)
    //          .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    //    }
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
