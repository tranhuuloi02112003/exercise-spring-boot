package com.loith.springhl.controller;

import com.loith.springhl.dto.request.UserCreateDtoRequest;
import com.loith.springhl.dto.response.User;
import com.loith.springhl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
  private final UserService service;

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public User createUser(@RequestBody UserCreateDtoRequest userCreateDtoRequest) {
    return service.createUser(userCreateDtoRequest);
  }
}
