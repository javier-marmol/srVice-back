package com.javier.srvice.employee.aplication;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.aplication.port.EmployeeServicePort;
import com.javier.srvice.employee.infrastructure.repository.EmployeeRepositoryJpa;
import com.javier.srvice.security.aplication.port.RolServicePort;
import com.javier.srvice.security.domain.User;
import com.javier.srvice.security.infrastructure.repository.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements EmployeeServicePort {

    @Autowired
    private EmployeeRepositoryJpa employeeRepositoryJpa;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private RolServicePort rolServicePort;

    @Override
    public Employee create(Employee employee, Integer idUser) {
        User user = userRepositoryJpa.findById(idUser).get();
        employee.setUser(user);
        Employee employeeToReturn = employeeRepositoryJpa.save(employee);
        return employeeToReturn;
    }
}
