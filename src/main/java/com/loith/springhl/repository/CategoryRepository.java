package com.loith.springhl.repository;

import com.loith.springhl.entity.CategoryEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, UUID> {}
