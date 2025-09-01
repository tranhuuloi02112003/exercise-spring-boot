package com.loith.springhl.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthResponse {
  private String accessToken;
  private String refreshToken;
}
