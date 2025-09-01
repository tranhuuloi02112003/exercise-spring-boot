package com.loith.springhl.controller;

import com.loith.springhl.dto.request.AuthDtoRequest;
import com.loith.springhl.dto.request.AuthRenewRequest;
import com.loith.springhl.dto.response.AuthResponse;
import com.loith.springhl.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
  public void logout(@RequestHeader("Authorization") String authorization) {
    authService.logout(authorization);
  }

  @PostMapping("/renew-token")
  public AuthResponse renewToken(@RequestBody AuthRenewRequest authRenewRequest) {
    return authService.renew(authRenewRequest);
  }
}
