package com.placemate.dto;

import com.placemate.entity.Student;
import lombok.*;

public class StudentDTO {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ProfileRequest {
        private String fullName;
        private String rollNumber;
        private String branch;
        private String year;
        private Double cgpa;
        private String phoneNumber;
        private String resumeUrl;
        private String skills;
        private String address;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ProfileResponse {
        private Long id;
        private String email;
        private String fullName;
        private String rollNumber;
        private String branch;
        private String year;
        private Double cgpa;
        private String phoneNumber;
        private String resumeUrl;
        private String skills;
        private String address;
        private Student.PlacementStatus placementStatus;
    }
}
