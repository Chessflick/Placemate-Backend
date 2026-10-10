package com.placemate.controller;

import com.placemate.dto.ApplicationDTO;
import com.placemate.dto.StudentDTO;
import com.placemate.service.ApplicationService;
import com.placemate.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    private final StudentService studentService;
    private final ApplicationService applicationService;

    @GetMapping("/profile")
    public ResponseEntity<StudentDTO.ProfileResponse> getProfile() {
        return ResponseEntity.ok(studentService.getMyProfile());
    }

    @PutMapping("/profile")
    public ResponseEntity<StudentDTO.ProfileResponse> updateProfile(@RequestBody StudentDTO.ProfileRequest request) {
        return ResponseEntity.ok(studentService.updateProfile(request));
    }

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<ApplicationDTO.ApplicationResponse> applyForJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.applyForJob(jobId));
    }

    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationDTO.ApplicationResponse>> getMyApplications() {
        return ResponseEntity.ok(applicationService.getMyApplications());
    }
}
