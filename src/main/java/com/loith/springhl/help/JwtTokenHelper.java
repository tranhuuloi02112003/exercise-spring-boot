package com.loith.springhl.help;

import com.loith.springhl.utils.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenHelper {

  @Value("${spring.security.secret-key}")
  private String secretKey;

  @Value("${spring.security.access-minutes}")
  private int accessMinutes;

  @Value("${spring.security.refresh-minutes}")
  private int refreshMinutes;

  private SecretKey getSignInKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String username, UUID tokenId) {
    return Jwts.builder()
        .subject(username)
        .claims(Map.of("tokenType", "accessToken", "tokenId", tokenId.toString()))
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(DateUtils.getTime(accessMinutes))
        .signWith(getSignInKey())
        .compact();
  }

  public String generateRefreshToken(String username, UUID tokenId) {
    return Jwts.builder()
        .subject(username)
        .claims(Map.of("tokenType", "refreshToken", "tokenId", tokenId.toString()))
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(DateUtils.getTime(refreshMinutes))
        .signWith(getSignInKey())
        .compact();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public String extractTokenType(String token) {
    return extractAllClaims(token).get("tokenType").toString();
  }

  public String extractTokenId(String token) {
    return extractAllClaims(token).get("tokenId", String.class);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
  }
}
