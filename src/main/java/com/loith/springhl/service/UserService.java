package com.loith.springhl.service;

import com.loith.springhl.configuration.UserDetailsCustom;
import com.loith.springhl.dto.request.UserCreateDtoRequest;
import com.loith.springhl.dto.response.User;

import com.loith.springhl.entity.UserEntity;

import com.loith.springhl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(UserCreateDtoRequest userCreateDtoRequest) {
        UserEntity userEntity = UserEntity.builder().username(userCreateDtoRequest.getUsername())
                .password(passwordEncoder.encode(userCreateDtoRequest.getPassword())).build();

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getPrincipal();
        log.info(userDetailsCustom.getUsername());
        log.info(String.valueOf(userDetailsCustom.getId()));

        userRepository.save(userEntity);
        return new User(userCreateDtoRequest.getUsername());
    }
}
