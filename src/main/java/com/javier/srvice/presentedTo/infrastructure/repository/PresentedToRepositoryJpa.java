package com.javier.srvice.presentedTo.infrastructure.repository;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.presentedTo.domain.PresentedTo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PresentedToRepositoryJpa extends JpaRepository<PresentedTo, Integer> {
    PresentedTo findByEmployeeAndJob(Employee employee, Job job);
}
