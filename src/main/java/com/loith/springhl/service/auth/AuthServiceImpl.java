package com.loith.springhl.service.auth;

import com.loith.springhl.dto.request.AuthDtoRequest;
import com.loith.springhl.dto.request.AuthRenewRequest;
import com.loith.springhl.dto.response.AuthResponse;
import com.loith.springhl.dto.response.Session;
import com.loith.springhl.entity.UserEntity;
import com.loith.springhl.help.JwtTokenHelper;
import com.loith.springhl.repository.UserRepository;
import com.loith.springhl.repository.redis.RedisRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private static final String BEARER_PREFIX = "Bearer ";

  private final AuthenticationManager authenticationManager;
  private final JwtTokenHelper jwtTokenHelper;
  private final UserRepository userRepository;
  private final RedisRepository redisRepository;

  @Override
  public AuthResponse login(AuthDtoRequest request) {
    // 1) Xác thực username/password
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    // 2) Phát hành access token
    UUID accessUuid = UUID.randomUUID();
    String accessToken = jwtTokenHelper.generateToken(request.getUsername(), accessUuid);

    // 3) Phát hành refresh token
    UUID refreshUuid = UUID.randomUUID();
    String refreshToken = jwtTokenHelper.generateRefreshToken(request.getUsername(), refreshUuid);

    // 4) Lưu refresh vào Redis để sau renew
    UserEntity user =
        userRepository
            .findByUsername(request.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    Session session =
        Session.builder()
            .refreshJti(refreshUuid)
            .userId(user.getId())
            .username(user.getUsername())
            .build();
    redisRepository.saveSession(session);

    return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
  }

  @Override
  @Transactional
  public ResponseEntity<Void> logout(String bearerToken) {
    if (bearerToken == null || !bearerToken.startsWith(BEARER_PREFIX)) {
      throw new BadCredentialsException("Authorization header is missing or invalid");
    }

    String access = bearerToken.substring(BEARER_PREFIX.length());

    // Tính TTL còn lại của access để blacklist đến đúng lúc hết hạn
    Instant exp = jwtTokenHelper.extractExpiration(access).toInstant();
    long ttlSec = Math.max(0L, Duration.between(Instant.now(), exp).getSeconds());

    if (ttlSec > 0) {
      String tokenId = jwtTokenHelper.extractTokenId(access);
      redisRepository.blacklist(tokenId, Duration.ofSeconds(ttlSec));
    }

    return ResponseEntity.noContent().build();
  }

  @Override
  @Transactional
  public AuthResponse renew(AuthRenewRequest request) {
    String refresh = request.getRefreshToken();
    if (refresh == null || refresh.isBlank()) {
      throw new BadCredentialsException("Refresh token is missing");
    }

    String tokenType = jwtTokenHelper.extractTokenType(refresh);
    if (!"refreshToken".equals(tokenType)) {
      throw new BadCredentialsException("Invalid token type");
    }

    String username = jwtTokenHelper.extractUsername(refresh);
    String refreshId = jwtTokenHelper.extractTokenId(refresh);
    if (refreshId == null || refreshId.isBlank()) {
      throw new BadCredentialsException("Invalid refresh token id");
    }

    UserEntity user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // Kiểm tra refresh tồn tại trong Redis
    if (!redisRepository.existsSession(refreshId)) {
      throw new BadCredentialsException("Refresh token is invalid or rotated");
    }

    // Xoá refresh cũ (rotate)
    redisRepository.deleteSession(refreshId);

    // Phát hành token mới
    UUID newAccessUuid = UUID.randomUUID();
    String newAccess = jwtTokenHelper.generateToken(username, newAccessUuid);

    UUID newRefreshUuid = UUID.randomUUID();
    String newRefresh = jwtTokenHelper.generateRefreshToken(username, newRefreshUuid);

    // Lưu refresh
    Session session =
        Session.builder()
            .refreshJti(newRefreshUuid)
            .userId(user.getId())
            .username(user.getUsername())
            .build();
    redisRepository.saveSession(session);

    return AuthResponse.builder().accessToken(newAccess).refreshToken(newRefresh).build();
  }
}
