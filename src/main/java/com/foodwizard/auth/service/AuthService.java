package com.foodwizard.auth.service;

import com.foodwizard.auth.dto.LoginRequestDto;

public interface AuthService {
    String login(LoginRequestDto loginRequest);
}
