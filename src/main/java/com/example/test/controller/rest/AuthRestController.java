package com.example.test.controller.rest;

import com.example.test.service.auth.IAuthService;
import com.example.test.service.auth.request.LoginRequest;
import com.example.test.service.auth.request.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auths")
public class AuthRestController {
    private final IAuthService authService;

    public AuthRestController(IAuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody  RegisterRequest request) {
        authService.register(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }
}