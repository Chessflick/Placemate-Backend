package com.placemate.service;

import com.placemate.dto.StudentDTO;
import com.placemate.entity.Student;
import com.placemate.entity.User;
import com.placemate.repository.StudentRepository;
import com.placemate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public StudentDTO.ProfileResponse getMyProfile() {
        User user = getCurrentUser();
        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));
        return mapToResponse(student);
    }

    public StudentDTO.ProfileResponse updateProfile(StudentDTO.ProfileRequest request) {
        User user = getCurrentUser();
        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        student.setFullName(request.getFullName());
        student.setRollNumber(request.getRollNumber());
        student.setBranch(request.getBranch());
        student.setYear(request.getYear());
        student.setCgpa(request.getCgpa());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setResumeUrl(request.getResumeUrl());
        student.setSkills(request.getSkills());
        student.setAddress(request.getAddress());

        studentRepository.save(student);
        return mapToResponse(student);
    }

    // Admin methods
    public List<StudentDTO.ProfileResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public StudentDTO.ProfileResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return mapToResponse(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    private StudentDTO.ProfileResponse mapToResponse(Student student) {
        return StudentDTO.ProfileResponse.builder()
                .id(student.getId())
                .email(student.getUser().getEmail())
                .fullName(student.getFullName())
                .rollNumber(student.getRollNumber())
                .branch(student.getBranch())
                .year(student.getYear())
                .cgpa(student.getCgpa())
                .phoneNumber(student.getPhoneNumber())
                .resumeUrl(student.getResumeUrl())
                .skills(student.getSkills())
                .address(student.getAddress())
                .placementStatus(student.getPlacementStatus())
                .build();
    }
}
