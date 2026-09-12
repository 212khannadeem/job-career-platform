package com.nkhan.job_career_platform.dto;

import com.nkhan.job_career_platform.entity.EmploymentType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class JobResponseDto {

    private Long id;
    private String title;
    private String companyName;
    private String description;
    private String location;
    private EmploymentType employmentType;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private Instant createdAt;
    private Instant updatedAt;
}