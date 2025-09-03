package com.loith.springhl.service.auth;

import com.loith.springhl.dto.request.AuthDtoRequest;
import com.loith.springhl.dto.request.AuthRenewRequest;
import com.loith.springhl.dto.response.AuthResponse;
import com.loith.springhl.entity.TokenEntity;
import com.loith.springhl.help.JwtTokenHelper;
import com.loith.springhl.repository.TokenRepository;
import com.loith.springhl.service.token.TokenBlacklistService;
import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final AuthenticationManager authenticationManager;
  private final JwtTokenHelper jwtTokenHelper;
  private final TokenRepository tokenRepository;
  private final TokenBlacklistService tokenBlacklistService;

  @Override
  public AuthResponse login(AuthDtoRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    String refreshToken = jwtTokenHelper.refreshToken(request.getUsername());
    TokenEntity savedRefresh =
        tokenRepository.save(TokenEntity.builder().refreshToken(refreshToken).build());

    String accessToken = jwtTokenHelper.generateToken(request.getUsername(), savedRefresh.getId());

    return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
  }

  @Override
  @Transactional
  public ResponseEntity<Void> logout(String bearerToken) {
    if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
      throw new BadCredentialsException("Authorization header is missing or invalid");
    }

    String access = bearerToken.substring("Bearer ".length());
    Instant tokenExp = jwtTokenHelper.extractExpiration(access).toInstant();

    long secondLeft = Math.max(0L, Duration.between(Instant.now(), tokenExp).getSeconds());

    if (secondLeft > 0) {
      String tokenId = jwtTokenHelper.extractTokenId(access);
      tokenBlacklistService.blacklist(tokenId, Duration.ofSeconds(secondLeft));
    }

    return ResponseEntity.noContent().build();
  }

  @Override
  @Transactional
  public AuthResponse renew(AuthRenewRequest request) {
    String tokenType = jwtTokenHelper.extractTokenType(request.getRefreshToken());
    if (!tokenType.equals("refreshToken")) {
      throw new BadCredentialsException("Invalid token type");
    }

    TokenEntity tokenExisted =
        tokenRepository
            .findByRefreshToken(request.getRefreshToken())
            .orElseThrow(() -> new BadCredentialsException("Refresh token not found"));

    if (tokenExisted.getAccessToken() != null) {
      throw new BadCredentialsException("Session logged out");
    }

    String username = jwtTokenHelper.extractUsername(request.getRefreshToken());

    String newRefreshToken = jwtTokenHelper.refreshToken(username);
    TokenEntity savedRefresh =
        tokenRepository.save(TokenEntity.builder().refreshToken(newRefreshToken).build());

    String newAccessToken = jwtTokenHelper.generateToken(username, savedRefresh.getId());

    return AuthResponse.builder().accessToken(newAccessToken).refreshToken(newRefreshToken).build();
  }
}
