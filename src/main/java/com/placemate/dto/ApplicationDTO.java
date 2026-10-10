package com.placemate.dto;

import com.placemate.entity.Application;
import lombok.*;

import java.time.LocalDateTime;

public class ApplicationDTO {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class StatusUpdateRequest {
        private Application.ApplicationStatus status;
        private String adminRemarks;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ApplicationResponse {
        private Long id;
        private Long studentId;
        private String studentName;
        private String rollNumber;
        private String branch;
        private Double cgpa;
        private Long jobId;
        private String jobTitle;
        private String companyName;
        private Application.ApplicationStatus status;
        private LocalDateTime appliedAt;
        private String adminRemarks;
    }
}
