package com.loith.springhl.repository;

import com.loith.springhl.entity.TokenEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {
  Optional<TokenEntity> findByAccessToken(String accessToken);

  Optional<TokenEntity> findByRefreshToken(String refreshToken);
}
