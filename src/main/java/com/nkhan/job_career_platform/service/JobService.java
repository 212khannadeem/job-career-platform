package com.nkhan.job_career_platform.service;

import com.nkhan.job_career_platform.dto.JobRequestDto;
import com.nkhan.job_career_platform.dto.JobResponseDto;
import com.nkhan.job_career_platform.entity.Job;
import com.nkhan.job_career_platform.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public JobResponseDto createJob(JobRequestDto request) {

        validateSalaryRange(
                request.getSalaryMin(),
                request.getSalaryMax()
        );

        Job job = new Job();

        job.setTitle(request.getTitle());
        job.setCompanyName(request.getCompanyName());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setEmploymentType(request.getEmploymentType());
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());

        Job savedJob = jobRepository.save(job);

        return mapToResponse(savedJob);
    }

    public JobResponseDto getJobById(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found")
                );

        return mapToResponse(job);
    }

    public List<JobResponseDto> getAllJobs() {

        return jobRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public JobResponseDto updateJob(
            Long id,
            JobRequestDto request) {

        validateSalaryRange(
                request.getSalaryMin(),
                request.getSalaryMax()
        );

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found")
                );

        job.setTitle(request.getTitle());
        job.setCompanyName(request.getCompanyName());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setEmploymentType(request.getEmploymentType());
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());

        Job updatedJob = jobRepository.save(job);

        return mapToResponse(updatedJob);
    }

    public void deleteJob(Long id) {

        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("Job not found");
        }

        jobRepository.deleteById(id);
    }

    private void validateSalaryRange(
            BigDecimal salaryMin,
            BigDecimal salaryMax) {

        if (salaryMin != null
                && salaryMax != null
                && salaryMin.compareTo(salaryMax) > 0) {

            throw new IllegalArgumentException(
                    "Minimum salary cannot be greater than maximum salary"
            );
        }
    }

    private JobResponseDto mapToResponse(Job job) {

        JobResponseDto response = new JobResponseDto();

        response.setId(job.getId());
        response.setTitle(job.getTitle());
        response.setCompanyName(job.getCompanyName());
        response.setDescription(job.getDescription());
        response.setLocation(job.getLocation());
        response.setEmploymentType(job.getEmploymentType());
        response.setSalaryMin(job.getSalaryMin());
        response.setSalaryMax(job.getSalaryMax());
        response.setCreatedAt(job.getCreatedAt());
        response.setUpdatedAt(job.getUpdatedAt());

        return response;
    }
}