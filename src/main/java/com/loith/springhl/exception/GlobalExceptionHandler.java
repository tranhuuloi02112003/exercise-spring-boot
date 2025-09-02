package com.loith.springhl.exception;

import com.loith.springhl.dto.response.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private ResponseEntity<ApiResponseDTO<Void>> error(HttpStatus st, String code, String msg) {
    return ResponseEntity.status(st)
        .body(ApiResponseDTO.<Void>builder().code(code).message(msg).build());
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiResponseDTO<Void>> handleBadCredentialsException(
      BadCredentialsException e) {
    return error(HttpStatus.UNAUTHORIZED, "AUTH_401", e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponseDTO<Void>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    String msg =
        e.getBindingResult().getFieldErrors().stream()
            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
            .distinct()
            .collect(java.util.stream.Collectors.joining("; "));
    return error(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", msg);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponseDTO<Void>> handleGenericException(Exception e) {
    e.printStackTrace();
    return error(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Unexpected error occurred");
  }
}
