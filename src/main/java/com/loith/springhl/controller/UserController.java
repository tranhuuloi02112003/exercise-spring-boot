package com.loith.springhl.controller;

import com.loith.springhl.dto.request.AuthDtoRequest;
import com.loith.springhl.dto.request.UserCreateDtoRequest;
import com.loith.springhl.dto.response.AuthResponse;
import com.loith.springhl.dto.response.User;
import com.loith.springhl.repository.UserRepository;
import com.loith.springhl.service.UserService;
import com.loith.springhl.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService service;
    private final UserRepository userRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User createProduct(@RequestBody UserCreateDtoRequest userCreateDtoRequest) {
        return service.createUser(userCreateDtoRequest);
    }

    @PostMapping("/token")
    public AuthResponse createToken(@RequestBody AuthDtoRequest authDtoRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDtoRequest.getUsername(), authDtoRequest.getPassword()));

        return AuthResponse.builder()
                .accessToken(JwtTokenUtils.generateToken(authDtoRequest.getUsername()))
                .refreshToken(JwtTokenUtils.refreshToken(authDtoRequest.getUsername()))
                .build();
    }
}
