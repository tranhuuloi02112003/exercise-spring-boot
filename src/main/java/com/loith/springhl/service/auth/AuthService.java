package com.loith.springhl.service.auth;

import com.loith.springhl.dto.request.AuthDtoRequest;
import com.loith.springhl.dto.request.AuthRenewRequest;
import com.loith.springhl.dto.response.AuthResponse;

public interface AuthService {
  AuthResponse login(AuthDtoRequest request);

  void logout(String bearerToken);

  AuthResponse renew(AuthRenewRequest request);
}
