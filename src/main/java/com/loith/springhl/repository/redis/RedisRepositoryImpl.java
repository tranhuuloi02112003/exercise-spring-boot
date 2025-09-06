package com.loith.springhl.repository.redis;

import com.loith.springhl.dto.response.Session;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {
  @Value("${spring.security.refresh-minutes}")
  private int refreshMinutes;

  private final RedisTemplate<String, Object> redis;

  private String sessionKey(String refreshJti) {
    return "sess:" + refreshJti;
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
  public void saveSession(Session session) {
    redis
        .opsForValue()
        .set(
            sessionKey(session.getRefreshJti().toString()),
            session,
            Duration.ofMinutes(refreshMinutes));
  }

  @Override
  public boolean existsSession(String tokenId) {
    return redis.hasKey(sessionKey(tokenId));
  }

  @Override
  public void deleteSession(String tokenId) {
    redis.delete(sessionKey(tokenId));
  }
}
