package com.nkhan.job_career_platform.controller;

import com.nkhan.job_career_platform.dto.*;
import com.nkhan.job_career_platform.entity.User;
import com.nkhan.job_career_platform.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        UserResponseDto userResponseDto = authService.register(registerRequestDto);
        return ResponseEntity.ok().body(userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @Valid @RequestBody LoginRequestDto request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}
