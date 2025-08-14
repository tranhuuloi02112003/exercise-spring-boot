package com.loith.springhl.dto;

import java.time.Instant;
import java.util.UUID;

public class ProductImage {
    private UUID id;

    private UUID productId;

    private String imageUrl;

    private Instant createdAt;

    private Instant updatedAt;
}
