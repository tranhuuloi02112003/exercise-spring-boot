package com.loith.springhl.service.token;

import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenStoreImpl implements RefreshTokenStore {
  @Value("${spring.security.refresh-minutes}")
  private int refreshMinutes;

  private final StringRedisTemplate redis;

  // Thêm id user để revoke-all theo user, quản lý đa thiết bị
  private static String key(UUID userId, String tokenId) {
    return "rt:%s:%s".formatted(userId, tokenId);
  }

  @Override
  public void save(UUID userId, String tokenId) {
    redis.opsForValue().set(key(userId, tokenId), "1", Duration.ofMinutes(refreshMinutes));
  }

  @Override
  public boolean exists(UUID userId, String tokenId) {
    return redis.hasKey(key(userId, tokenId));
  }

  @Override
  public void delete(UUID userId, String tokenId) {
    redis.delete(key(userId, tokenId));
  }
}
