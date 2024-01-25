package com.example.test.service.auth;

import com.example.test.service.auth.request.LoginRequest;
import com.example.test.service.auth.request.RegisterRequest;

public interface IAuthService {

    String login(LoginRequest request);

    void register(RegisterRequest request);
}
