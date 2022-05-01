package com.javier.srvice.employee.infrastructure.repository;

import com.javier.srvice.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepositoryJpa extends JpaRepository<Employee, Integer> {
}
