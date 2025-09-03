package com.loith.springhl.service.token;

import java.time.Duration;

public interface TokenBlacklistService {
  void blacklist(String tokenId, Duration ttl);

  boolean isBlacklisted(String tokenId);
}
