package com.loith.springhl.repository;

import com.loith.springhl.entity.ProductEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, UUID> {}
