package com.nkhan.job_career_platform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequestDto {

    @Size(max = 100, message = "First name cannot exceed 100 characters")
    private String firstName;

    @Size(max = 100, message = "Last name cannot exceed 100 characters")
    private String lastName;

    @Email(message = "Please provide a valid email")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    private String email;

    @Size(max = 20, message = "Phone cannot exceed 20 characters")
    private String phone;

    private LocalDate dateOfBirth;

    @Size(max = 255, message = "Location cannot exceed 255 characters")
    private String location;

    private String bio;

    @Size(max = 500, message = "Profile image URL cannot exceed 500 characters")
    private String profileImageUrl;
}
