package com.loith.springhl.repository.redis;

import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {
  @Value("${spring.security.refresh-minutes}")
  private int refreshMinutes;

  private final StringRedisTemplate redis;

  // Thêm id user để revoke-all theo user, quản lý đa thiết bị
  private static String refreshKey(UUID userId, String refreshId) {
    return "rt:%s:%s".formatted(userId, refreshId);
  }

  private static String blacklistKey(String accessJti) {
    return "bl:%s".formatted(accessJti);
  }

  @Override
  public void blacklist(String tokenId, Duration ttl) {
    redis.opsForValue().set(blacklistKey(tokenId), "1", ttl);
  }

  @Override
  public boolean isBlacklisted(String tokenId) {
    return redis.hasKey(blacklistKey(tokenId));
  }

  @Override
  public void save(UUID userId, String tokenId) {
    redis.opsForValue().set(refreshKey(userId, tokenId), "1", Duration.ofMinutes(refreshMinutes));
  }

  @Override
  public boolean exists(UUID userId, String tokenId) {
    return redis.hasKey(refreshKey(userId, tokenId));
  }

  @Override
  public void delete(UUID userId, String tokenId) {
    redis.delete(refreshKey(userId, tokenId));
  }
}
