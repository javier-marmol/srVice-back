package com.javier.srvice.job.infrastructure.repository;

import com.javier.srvice.job.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepositoryJpa extends JpaRepository<Job, Integer> {
}
