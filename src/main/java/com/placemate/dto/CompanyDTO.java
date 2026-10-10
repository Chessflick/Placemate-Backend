package com.placemate.dto;

import lombok.*;

public class CompanyDTO {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class CompanyRequest {
        private String name;
        private String industry;
        private String website;
        private String description;
        private String logoUrl;
        private String location;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class CompanyResponse {
        private Long id;
        private String name;
        private String industry;
        private String website;
        private String description;
        private String logoUrl;
        private String location;
        private int jobCount;
    }
}
