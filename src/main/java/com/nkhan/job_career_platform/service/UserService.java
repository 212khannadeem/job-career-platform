package com.nkhan.job_career_platform.service;

import com.nkhan.job_career_platform.dto.PageResponseDto;
import com.nkhan.job_career_platform.entity.User;
import com.nkhan.job_career_platform.exception.DuplicateResourceException;
import com.nkhan.job_career_platform.exception.ResourceNotFoundException;
import com.nkhan.job_career_platform.repository.UserRepository;
import com.nkhan.job_career_platform.dto.UserCreateRequestDto;
import com.nkhan.job_career_platform.dto.UserResponseDto;
import com.nkhan.job_career_platform.dto.UserUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto createUser(UserCreateRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already exists"
            );
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setLocation(request.getLocation());
        user.setBio(request.getBio());
        user.setProfileImageUrl(request.getProfileImageUrl());

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    public PageResponseDto<UserResponseDto> getAllUsers(
            String search,
            Pageable pageable) {

        Page<User> users;

        if (search == null || search.isBlank()) {

            users = userRepository.findAll(pageable);

        } else {

            users = userRepository
                    .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                            search,
                            search,
                            search,
                            pageable
                    );
        }

        Page<UserResponseDto> userPage =
                users.map(this::mapToResponse);

        return new PageResponseDto<>(
                userPage.getContent(),
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isFirst(),
                userPage.isLast()
        );
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id));

        return mapToResponse(user);
    }

    public UserResponseDto updateUser(
            Long id,
            UserUpdateRequestDto request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setLocation(request.getLocation());
        user.setBio(request.getBio());
        user.setProfileImageUrl(request.getProfileImageUrl());

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id));

        userRepository.delete(user);
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

    public UserResponseDto getCurrentUser(User user) {
        return mapToResponse(user);
    }
}