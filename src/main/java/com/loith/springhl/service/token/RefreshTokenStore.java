package com.loith.springhl.service.token;

import java.time.Duration;
import java.util.UUID;

public interface RefreshTokenStore {
  void save(UUID userId, String tokenId, Duration ttl);

  boolean exists(UUID userId, String tokenId);

  void delete(UUID userId, String tokenId);
}
