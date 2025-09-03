package com.loith.springhl.controller;

import com.loith.springhl.dto.request.AuthDtoRequest;
import com.loith.springhl.dto.request.AuthRenewRequest;
import com.loith.springhl.dto.response.AuthResponse;
import com.loith.springhl.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/token")
  public AuthResponse createToken(@RequestBody AuthDtoRequest authDtoRequest) {
    return authService.login(authDtoRequest);
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorization) {
    return authService.logout(authorization);
  }

  @PostMapping("/renew-token")
  public AuthResponse renewToken(@RequestBody AuthRenewRequest authRenewRequest) {
    return authService.renew(authRenewRequest);
  }
}
