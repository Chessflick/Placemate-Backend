package com.placemate.service;

import com.placemate.dto.ApplicationDTO;
import com.placemate.entity.*;
import com.placemate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    private Student getCurrentStudent() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public ApplicationDTO.ApplicationResponse applyForJob(Long jobId) {
        Student student = getCurrentStudent();
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (job.getStatus() == Job.JobStatus.CLOSED) {
            throw new RuntimeException("Job is no longer accepting applications");
        }

        if (applicationRepository.existsByStudentIdAndJobId(student.getId(), jobId)) {
            throw new RuntimeException("Already applied for this job");
        }

        Application application = Application.builder()
                .student(student)
                .job(job)
                .build();

        application = applicationRepository.save(application);
        return mapToResponse(application);
    }

    public List<ApplicationDTO.ApplicationResponse> getMyApplications() {
        Student student = getCurrentStudent();
        return applicationRepository.findByStudent(student).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Admin methods
    public List<ApplicationDTO.ApplicationResponse> getApplicationsByJob(Long jobId) {
        return applicationRepository.findByJobId(jobId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ApplicationDTO.ApplicationResponse> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ApplicationDTO.ApplicationResponse updateApplicationStatus(Long applicationId,
                                                                       ApplicationDTO.StatusUpdateRequest request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(request.getStatus());
        application.setAdminRemarks(request.getAdminRemarks());

        // If selected, mark student as placed
        if (request.getStatus() == Application.ApplicationStatus.SELECTED) {
            Student student = application.getStudent();
            student.setPlacementStatus(Student.PlacementStatus.PLACED);
            studentRepository.save(student);
        }

        applicationRepository.save(application);
        return mapToResponse(application);
    }

    private ApplicationDTO.ApplicationResponse mapToResponse(Application app) {
        return ApplicationDTO.ApplicationResponse.builder()
                .id(app.getId())
                .studentId(app.getStudent().getId())
                .studentName(app.getStudent().getFullName())
                .rollNumber(app.getStudent().getRollNumber())
                .branch(app.getStudent().getBranch())
                .cgpa(app.getStudent().getCgpa())
                .jobId(app.getJob().getId())
                .jobTitle(app.getJob().getTitle())
                .companyName(app.getJob().getCompany().getName())
                .status(app.getStatus())
                .appliedAt(app.getAppliedAt())
                .adminRemarks(app.getAdminRemarks())
                .build();
    }
}
