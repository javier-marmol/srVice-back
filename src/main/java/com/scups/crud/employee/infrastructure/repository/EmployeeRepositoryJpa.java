package com.scups.crud.employee.infrastructure.repository;

import com.scups.crud.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepositoryJpa extends JpaRepository<Employee, Integer> {
}
