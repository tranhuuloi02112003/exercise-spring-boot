package com.loith.springhl.repository;

import com.loith.springhl.entity.CategoryEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, UUID> {

  List<CategoryEntity> findByIdIn(List<UUID> uuidList);
}
