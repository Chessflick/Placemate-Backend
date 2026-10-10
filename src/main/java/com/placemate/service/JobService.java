package com.placemate.service;

import com.placemate.dto.JobDTO;
import com.placemate.entity.*;
import com.placemate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ApplicationRepository applicationRepository;

    public JobDTO.JobResponse createJob(JobDTO.JobRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Job job = Job.builder()
                .company(company)
                .title(request.getTitle())
                .description(request.getDescription())
                .eligibility(request.getEligibility())
                .ctc(request.getCtc())
                .location(request.getLocation())
                .jobType(request.getJobType())
                .minCgpa(request.getMinCgpa())
                .lastDateToApply(request.getLastDateToApply())
                .build();

        job = jobRepository.save(job);
        return mapToResponse(job, false);
    }

    public JobDTO.JobResponse updateJob(Long id, JobDTO.JobRequest request) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        job.setCompany(company);
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setEligibility(request.getEligibility());
        job.setCtc(request.getCtc());
        job.setLocation(request.getLocation());
        job.setJobType(request.getJobType());
        job.setMinCgpa(request.getMinCgpa());
        job.setLastDateToApply(request.getLastDateToApply());

        jobRepository.save(job);
        return mapToResponse(job, false);
    }

    public List<JobDTO.JobResponse> getAllOpenJobs() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElse(null);
        Student student = (user != null) ? studentRepository.findByUser(user).orElse(null) : null;

        return jobRepository.findByStatusOrderByLastDateToApplyAsc(Job.JobStatus.OPEN)
                .stream()
                .map(job -> {
                    boolean applied = student != null &&
                            applicationRepository.existsByStudentIdAndJobId(student.getId(), job.getId());
                    return mapToResponse(job, applied);
                })
                .collect(Collectors.toList());
    }

    public List<JobDTO.JobResponse> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(job -> mapToResponse(job, false))
                .collect(Collectors.toList());
    }

    public JobDTO.JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return mapToResponse(job, false);
    }

    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    public JobDTO.JobResponse toggleJobStatus(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        job.setStatus(job.getStatus() == Job.JobStatus.OPEN ? Job.JobStatus.CLOSED : Job.JobStatus.OPEN);
        jobRepository.save(job);
        return mapToResponse(job, false);
    }

    private JobDTO.JobResponse mapToResponse(Job job, boolean applied) {
        return JobDTO.JobResponse.builder()
                .id(job.getId())
                .companyId(job.getCompany().getId())
                .companyName(job.getCompany().getName())
                .companyLogo(job.getCompany().getLogoUrl())
                .title(job.getTitle())
                .description(job.getDescription())
                .eligibility(job.getEligibility())
                .ctc(job.getCtc())
                .location(job.getLocation())
                .jobType(job.getJobType())
                .minCgpa(job.getMinCgpa())
                .lastDateToApply(job.getLastDateToApply())
                .status(job.getStatus())
                .alreadyApplied(applied)
                .build();
    }
}
