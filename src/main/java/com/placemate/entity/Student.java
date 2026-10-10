package com.placemate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "students")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true)
    private String rollNumber;

    private String branch;
    private String year;
    private Double cgpa;
    private String phoneNumber;
    private String resumeUrl;
    private String skills;
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PlacementStatus placementStatus = PlacementStatus.NOT_PLACED;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Application> applications;

    public enum PlacementStatus {
        NOT_PLACED, PLACED
    }
}
