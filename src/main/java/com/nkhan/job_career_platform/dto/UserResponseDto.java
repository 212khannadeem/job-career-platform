package com.nkhan.job_career_platform.dto;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
public class UserResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String location;
    private String bio;
    private String profileImageUrl;
    private Instant createdAt;
    private Instant updatedAt;
}
