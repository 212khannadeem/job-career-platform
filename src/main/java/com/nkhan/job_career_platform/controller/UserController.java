package com.nkhan.job_career_platform.controller;

import com.nkhan.job_career_platform.dto.PageResponseDto;
import com.nkhan.job_career_platform.entity.User;
import com.nkhan.job_career_platform.security.CustomUserDetails;
import com.nkhan.job_career_platform.service.UserService;
import com.nkhan.job_career_platform.dto.UserCreateRequestDto;
import com.nkhan.job_career_platform.dto.UserResponseDto;
import com.nkhan.job_career_platform.dto.UserUpdateRequestDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateRequestDto user) {
        UserResponseDto newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<UserResponseDto>> getAllUsers(@RequestParam(required = false) String search, Pageable pageable) {
        PageResponseDto<UserResponseDto> users = userService.getAllUsers(search, pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,@Valid @RequestBody UserUpdateRequestDto user) {
        UserResponseDto updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        return ResponseEntity.ok(
                userService.getCurrentUser(user)
        );
    }
}
