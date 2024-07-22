package com.leshkins.faceittask.repository;

import com.leshkins.faceittask.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
    void deleteAll();
    Page<Job> findAll(Pageable pageable);
}
