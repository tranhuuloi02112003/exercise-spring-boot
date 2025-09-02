package com.loith.springhl.controller;

import com.loith.springhl.dto.request.AuthDtoRequest;
import com.loith.springhl.dto.request.AuthRenewRequest;
import com.loith.springhl.dto.response.AuthResponse;
import com.loith.springhl.repository.TokenRepository;
import com.loith.springhl.service.auth.AuthService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final TokenRepository tokenRepository;

  @PostMapping("/token")
  public AuthResponse createToken(@RequestBody AuthDtoRequest authDtoRequest) {
    return authService.login(authDtoRequest);
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logout(@RequestHeader("Authorization") String authorization) {
    authService.logout(authorization);
  }

  @PostMapping("/renew-token")
  public AuthResponse renewToken(@RequestBody AuthRenewRequest authRenewRequest) {
    return authService.renew(authRenewRequest);
  }

  @Scheduled(cron = "* */7 * * * *", zone = "Asia/Ho_Chi_Minh")
  public void purgeTokens() {
    LocalDateTime threshold = LocalDateTime.now().minusMinutes(7);
    int deleted = tokenRepository.deleteExpiredOrLoggedOut(threshold);
    if (deleted > 0) {
      log.info("Purged {} token rows (logged-out or created_at > 10m)", deleted);
    }
  }
}
