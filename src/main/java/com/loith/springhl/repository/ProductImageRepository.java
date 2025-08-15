package com.loith.springhl.repository;

import com.loith.springhl.entity.ProductImageEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends CrudRepository<ProductImageEntity, UUID> {}
