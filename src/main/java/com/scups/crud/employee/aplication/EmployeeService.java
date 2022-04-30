package com.scups.crud.employee.aplication;

import com.scups.crud.employee.aplication.port.EmployeeServicePort;
import com.scups.crud.employee.domain.Employee;
import com.scups.crud.employee.infrastructure.repository.EmployeeRepositoryJpa;
import com.scups.crud.security.domain.User;
import com.scups.crud.security.infrastructure.repository.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.Optional;

@Service
public class EmployeeService implements EmployeeServicePort {

    @Autowired
    private EmployeeRepositoryJpa employeeRepositoryJpa;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Override
    public Employee create(Employee employee, Integer idUser) {
        User user = userRepositoryJpa.findById(idUser).get();
        employee.setUser(user);
        return employeeRepositoryJpa.save(employee);
    }
}
