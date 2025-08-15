package com.loith.springhl.repository;

import com.loith.springhl.entity.ProductEntity;
import com.loith.springhl.projection.ProductCategoryProjection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, UUID> {

    @Query("""
    SELECT
        p.id AS "idProduct",
        p.name AS "nameProduct",
        p.description AS "description",
        p.price AS "price",
        c.name AS "categoryName"
    FROM products p
    JOIN categories c ON p.category_id = c.id
""")
    List<ProductCategoryProjection> findAllProductCategoryInfo();
}
