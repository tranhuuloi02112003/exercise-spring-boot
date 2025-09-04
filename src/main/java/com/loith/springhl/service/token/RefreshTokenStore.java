package com.loith.springhl.service.token;

import java.util.UUID;

public interface RefreshTokenStore {
  void save(UUID userId, String tokenId);

  boolean exists(UUID userId, String tokenId);

  void delete(UUID userId, String tokenId);
}
