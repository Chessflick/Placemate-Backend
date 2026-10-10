package com.placemate.repository;

import com.placemate.entity.Application;
import com.placemate.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudent(Student student);
    List<Application> findByJobId(Long jobId);
    boolean existsByStudentIdAndJobId(Long studentId, Long jobId);
    Optional<Application> findByStudentIdAndJobId(Long studentId, Long jobId);
    List<Application> findByStatus(Application.ApplicationStatus status);
}
