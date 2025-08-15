package com.loith.springhl.projection;

import org.springframework.data.relational.core.mapping.Column;

import java.util.UUID;

public record ProductCategoryProjection(
        @Column("idProduct") UUID idProduct,
        @Column("nameProduct") String nameProduct,
        @Column("description") String description,
        @Column("price") double price,
        @Column("categoryName") String categoryName
) {}
