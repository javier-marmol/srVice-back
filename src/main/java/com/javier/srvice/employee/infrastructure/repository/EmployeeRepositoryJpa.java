package com.javier.srvice.employee.infrastructure.repository;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepositoryJpa extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByUser(User user);
}
