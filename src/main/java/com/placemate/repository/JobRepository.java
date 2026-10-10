package com.placemate.repository;

import com.placemate.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByStatus(Job.JobStatus status);
    List<Job> findByCompanyId(Long companyId);
    List<Job> findByStatusOrderByLastDateToApplyAsc(Job.JobStatus status);
}
