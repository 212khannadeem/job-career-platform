package com.nkhan.job_career_platform.service;

import com.nkhan.job_career_platform.dto.AuthResponseDto;
import com.nkhan.job_career_platform.dto.LoginRequestDto;
import com.nkhan.job_career_platform.dto.RegisterRequestDto;
import com.nkhan.job_career_platform.dto.UserResponseDto;
import com.nkhan.job_career_platform.entity.User;
import com.nkhan.job_career_platform.exception.DuplicateResourceException;
import com.nkhan.job_career_platform.exception.ResourceNotFoundException;
import com.nkhan.job_career_platform.repository.UserRepository;
import com.nkhan.job_career_platform.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponseDto register(RegisterRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        // 2. Create User entity
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        // 3. Hash password
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // 4. Save user
        User newUser =  userRepository.save(user);

        return mapToResponse(newUser);
    }

    public AuthResponseDto login(LoginRequestDto request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid email or password")
                );

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new ResourceNotFoundException(
                    "Invalid email or password"
            );
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponseDto(token);
    }

    private UserResponseDto mapToResponse(User user) {

        UserResponseDto response = new UserResponseDto();

        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setDateOfBirth(user.getDateOfBirth());
        response.setLocation(user.getLocation());
        response.setBio(user.getBio());
        response.setProfileImageUrl(user.getProfileImageUrl());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        return response;
    }
}