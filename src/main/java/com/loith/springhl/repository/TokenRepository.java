package com.loith.springhl.repository;

import com.loith.springhl.entity.TokenEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {
  Optional<TokenEntity> findByAccessToken(String accessToken);

  Optional<TokenEntity> findByRefreshToken(String refreshToken);

  @Modifying
  @Transactional
  @Query(
      value =
          """
        DELETE FROM tokens
        WHERE access_token IS NOT NULL
           OR created_at < :threshold
    """,
      nativeQuery = true)
  int deleteExpiredOrLoggedOut(@Param("threshold") LocalDateTime threshold);
}
