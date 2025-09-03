package com.loith.springhl.service.token;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {
  private final StringRedisTemplate redis;

  private static String key(String tokenId) {
    return "bl:" + tokenId;
  }

  @Override
  public void blacklist(String tokenId, Duration ttl) {
    redis.opsForValue().set(key(tokenId), "1", ttl);
  }

  @Override
  public boolean isBlacklisted(String tokenId) {
    return redis.hasKey(key(tokenId));
  }
}
