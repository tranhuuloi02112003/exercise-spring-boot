package com.loith.springhl.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Chuyển qa Json ẩn các filed null
public class ApiResponseDTO<T> {
  private String code;
  private String message;
  private T result;
}
