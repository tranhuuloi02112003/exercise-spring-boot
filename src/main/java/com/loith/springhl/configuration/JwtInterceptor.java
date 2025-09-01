package com.loith.springhl.configuration;

import com.loith.springhl.entity.UserEntity;
import com.loith.springhl.repository.UserRepository;
import com.loith.springhl.utils.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtInterceptor extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String prefix = "Bearer ";
        String tokenWithoutPrefix = token.replace(prefix, "");
        String username = JwtTokenUtils.extractUsername(tokenWithoutPrefix);
        if (username != null) {
            UserEntity userEntity = userRepository.findByUsername(username).orElseThrow();

            UserDetailsCustom userDetailsCustom =
                    UserDetailsCustom.builder()
                            .username(username)
                            .password(userEntity.getPassword())
                            .id(userEntity.getId())
                            .build();

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetailsCustom, null, userDetailsCustom.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
