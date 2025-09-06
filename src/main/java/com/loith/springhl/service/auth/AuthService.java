package com.loith.springhl.service.auth;

import com.loith.springhl.dto.request.AuthDtoRequest;
import com.loith.springhl.dto.request.AuthRenewRequest;
import com.loith.springhl.dto.response.AuthResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
  AuthResponse login(AuthDtoRequest request);

  ResponseEntity<Void> logout(String bearerToken);

  AuthResponse renew(AuthRenewRequest request);
}
