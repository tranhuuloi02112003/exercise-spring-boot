package com.loith.springhl.repository.redis;

import java.time.Duration;
import java.util.UUID;

public interface RedisRepository {

  void blacklist(String tokenId, Duration ttl);

  boolean isBlacklisted(String tokenId);

  void save(UUID userId, String tokenId);

  boolean exists(UUID userId, String tokenId);

  void delete(UUID userId, String tokenId);
}
