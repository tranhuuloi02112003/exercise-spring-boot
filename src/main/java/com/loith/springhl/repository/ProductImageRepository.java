package com.loith.springhl.repository;

import com.loith.springhl.entity.ImageEntity;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ImageEntity, UUID> {}
