package com.loith.springhl.repository;

import com.loith.springhl.entity.ProductImageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProductImageRepository extends CrudRepository<ProductImageEntity, UUID> {
}
