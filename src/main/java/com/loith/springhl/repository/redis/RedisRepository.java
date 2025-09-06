package com.loith.springhl.repository.redis;

import com.loith.springhl.dto.response.Session;
import java.time.Duration;

public interface RedisRepository {

  void blacklist(String tokenId, Duration ttl);

  boolean isBlacklisted(String tokenId);

  void saveSession(Session session);

  boolean existsSession(String tokenId);

  void deleteSession(String tokenId);
}
