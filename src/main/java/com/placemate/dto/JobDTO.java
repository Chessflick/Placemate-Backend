package com.placemate.dto;

import com.placemate.entity.Job;
import lombok.*;

import java.time.LocalDate;

public class JobDTO {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class JobRequest {
        private Long companyId;
        private String title;
        private String description;
        private String eligibility;
        private Double ctc;
        private String location;
        private String jobType;
        private Double minCgpa;
        private LocalDate lastDateToApply;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class JobResponse {
        private Long id;
        private Long companyId;
        private String companyName;
        private String companyLogo;
        private String title;
        private String description;
        private String eligibility;
        private Double ctc;
        private String location;
        private String jobType;
        private Double minCgpa;
        private LocalDate lastDateToApply;
        private Job.JobStatus status;
        private boolean alreadyApplied;
    }
}
