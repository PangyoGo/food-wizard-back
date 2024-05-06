package com.foodwizard.auth.service;

import com.foodwizard.auth.domain.User;
import com.foodwizard.auth.dto.LoginRequestDto;
import com.foodwizard.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemoryAuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    @Override
    public String login(LoginRequestDto loginRequest) {

        if (!loginRequest.getUsername().equals("user")) {
            throw new UsernameNotFoundException("User not found");
        }

        if (!encoder.matches(loginRequest.getPassword(), "$2a$10$V8TdSOdddx3QepgoakhZludWWHieRVl26oJwEHjQyehKCW/gcLQPq")) {
            throw new BadCredentialsException("Invalid password");
        }

        return jwtUtil.createAccessToken(User.builder()
                .id("user")
                .name("user")
                .build());
    }
}
