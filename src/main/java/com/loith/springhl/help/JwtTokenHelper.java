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

  private SecretKey getSignInKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String username, UUID uuid) {
    return Jwts.builder()
        .subject(username)
        .claims(Map.of("tokenType", "accessToken", "tokenId", uuid))
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(DateUtils.getTime(5))
        .signWith(getSignInKey())
        .compact();
  }

  public String refreshToken(String username) {
    return Jwts.builder()
        .subject(username)
        .claims(Map.of("tokenType", "refreshToken"))
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(DateUtils.getTime(7))
        .signWith(getSignInKey())
        .compact();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public String extractId(String token) {
    return extractClaim(token, Claims::getId);
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
