package com.loith.springhl.unittest;

import com.loith.springhl.dto.Product;
import com.loith.springhl.dto.ProductCreateDtoRequest;
import com.loith.springhl.dto.ProductUpdateDtoRequest;
import com.loith.springhl.entity.ProductEntity;
import com.loith.springhl.repository.ProductRepository;
import com.loith.springhl.service.ProductService;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {
  @Mock private ProductRepository productRepository;

  @InjectMocks private ProductService productService;

  @Test
  void testDeleteProduct() {
    UUID uuid = UUID.randomUUID();

    Instant createdAt = Instant.now();
    Instant updatedAt = Instant.now();

    Mockito.when(productRepository.findById(uuid))
        .thenReturn(
            Optional.of(new ProductEntity(uuid, "name", "desc", 1000, createdAt, updatedAt)));

    Mockito.doNothing().when(productRepository).deleteById(uuid);

    productService.deleteById(uuid);

    Mockito.verify(productRepository, Mockito.times(1)).deleteById(uuid);
  }

  @Test
  void testDeleteProductWithIdNotFound() {
    UUID uuid = UUID.randomUUID();

    Mockito.when(productRepository.findById(uuid)).thenReturn(Optional.empty());

    Assertions.assertThrows(IllegalArgumentException.class, () -> productService.deleteById(uuid));
  }

  @Test
  void testSaveProduct() {
    UUID uuid = UUID.randomUUID();

    ProductCreateDtoRequest productCreateDtoRequest = new ProductCreateDtoRequest();
    productCreateDtoRequest.setName("name");
    productCreateDtoRequest.setDescription("desc");
    productCreateDtoRequest.setPrice(100);

    ProductEntity savedEntity =
        new ProductEntity(
            uuid,
            productCreateDtoRequest.getName(),
            productCreateDtoRequest.getDescription(),
            productCreateDtoRequest.getPrice(),
            Instant.now(),
            Instant.now());

    Mockito.when(productRepository.save(Mockito.any(ProductEntity.class))).thenReturn(savedEntity);

    Product product = productService.createProduct(productCreateDtoRequest);

    Mockito.verify(productRepository).save(Mockito.any(ProductEntity.class));

    Assertions.assertEquals(savedEntity.getName(), product.getName());
    Assertions.assertEquals(savedEntity.getDescription(), product.getDescription());
    Assertions.assertEquals(savedEntity.getPrice(), product.getPrice());
  }

  @Test
  void testGetAllProducts() {
    Instant date = Instant.now();
    List<ProductEntity> productEntities =
        List.of(
            new ProductEntity(UUID.randomUUID(), "name", "description", 1000, date, date),
            new ProductEntity(UUID.randomUUID(), "name", "description", 1000, date, date));

    Iterable<ProductEntity> productEntityIterables = productEntities;
    Mockito.when(productRepository.findAll()).thenReturn(productEntityIterables);

    List<Product> products = productService.getAll();

    for (int i = 0; i < products.size(); i++) {
      Assertions.assertEquals(products.get(i).getId(), productEntities.get(i).getId());
      Assertions.assertEquals(products.get(i).getName(), productEntities.get(i).getName());
      Assertions.assertEquals(
          products.get(i).getDescription(), productEntities.get(i).getDescription());
      Assertions.assertEquals(products.get(i).getPrice(), productEntities.get(i).getPrice());
      Assertions.assertEquals(
          products.get(i).getCreatedAt(), productEntities.get(i).getCreatedAt());
      Assertions.assertEquals(
          products.get(i).getUpdatedAt(), productEntities.get(i).getUpdatedAt());
    }
  }

  @Test
  void testFindByIdWithProductExists() {
    UUID uuid = UUID.randomUUID();
    Instant createdAt = Instant.now().minusSeconds(3600);
    Instant updatedAt = Instant.now();

    ProductEntity productEntity =
        new ProductEntity(uuid, "Test Name", "Test Description", 1500, createdAt, updatedAt);

    Mockito.when(productRepository.findById(uuid)).thenReturn(Optional.of(productEntity));

    Product result = productService.getById(uuid);

    Assertions.assertEquals(productEntity.getId(), result.getId());
    Assertions.assertEquals(productEntity.getName(), result.getName());
    Assertions.assertEquals(productEntity.getDescription(), result.getDescription());
    Assertions.assertEquals(productEntity.getPrice(), result.getPrice());
    Assertions.assertEquals(productEntity.getCreatedAt(), result.getCreatedAt());
    Assertions.assertEquals(productEntity.getUpdatedAt(), result.getUpdatedAt());

    Mockito.verify(productRepository, Mockito.times(1)).findById(uuid);
  }

  @Test
  void testUpdateProduct() {
    UUID uuid = UUID.randomUUID();
    Instant createdAt = Instant.now().minusSeconds(3600);
    Instant oldUpdatedAt = Instant.now().minusSeconds(1800);

    ProductEntity existingEntity =
        new ProductEntity(uuid, "Old Name", "Old Description", 1000, createdAt, oldUpdatedAt);

    ProductUpdateDtoRequest updateRequest = new ProductUpdateDtoRequest();
    updateRequest.setName("New Name");
    updateRequest.setDescription("New Description");
    updateRequest.setPrice(2000);

    Instant newUpdatedAt = Instant.now();
    ProductEntity updatedEntity =
        new ProductEntity(
            uuid,
            updateRequest.getName(),
            updateRequest.getDescription(),
            updateRequest.getPrice(),
            createdAt,
            newUpdatedAt);

    Mockito.when(productRepository.findById(uuid)).thenReturn(Optional.of(existingEntity));
    Mockito.when(productRepository.save(Mockito.any(ProductEntity.class)))
        .thenReturn(updatedEntity);

    Product result = productService.updateProduct(uuid, updateRequest);

    Assertions.assertEquals(updatedEntity.getId(), result.getId());
    Assertions.assertEquals(updatedEntity.getName(), result.getName());
    Assertions.assertEquals(updatedEntity.getDescription(), result.getDescription());
    Assertions.assertEquals(updatedEntity.getPrice(), result.getPrice());
    Assertions.assertEquals(updatedEntity.getCreatedAt(), result.getCreatedAt());
    Assertions.assertEquals(updatedEntity.getUpdatedAt(), result.getUpdatedAt());

    Mockito.verify(productRepository, Mockito.times(1)).findById(uuid);
    Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(ProductEntity.class));
  }
}
