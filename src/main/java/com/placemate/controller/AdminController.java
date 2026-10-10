package com.placemate.controller;

import com.placemate.dto.*;
import com.placemate.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final StudentService studentService;
    private final CompanyService companyService;
    private final ApplicationService applicationService;

    // ── Students ──────────────────────────────────────────
    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO.ProfileResponse>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDTO.ProfileResponse> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    // ── Companies ─────────────────────────────────────────
    @GetMapping("/companies")
    public ResponseEntity<List<CompanyDTO.CompanyResponse>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<CompanyDTO.CompanyResponse> getCompany(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @PostMapping("/companies")
    public ResponseEntity<CompanyDTO.CompanyResponse> addCompany(@RequestBody CompanyDTO.CompanyRequest request) {
        return ResponseEntity.ok(companyService.addCompany(request));
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<CompanyDTO.CompanyResponse> updateCompany(@PathVariable Long id,
                                                                     @RequestBody CompanyDTO.CompanyRequest request) {
        return ResponseEntity.ok(companyService.updateCompany(id, request));
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }

    // ── Applications ──────────────────────────────────────
    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationDTO.ApplicationResponse>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/applications/job/{jobId}")
    public ResponseEntity<List<ApplicationDTO.ApplicationResponse>> getApplicationsByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsByJob(jobId));
    }

    @PutMapping("/applications/{id}/status")
    public ResponseEntity<ApplicationDTO.ApplicationResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody ApplicationDTO.StatusUpdateRequest request) {
        return ResponseEntity.ok(applicationService.updateApplicationStatus(id, request));
    }
}
