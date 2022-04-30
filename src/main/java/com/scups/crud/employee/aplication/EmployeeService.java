package com.scups.crud.employee.aplication;

import com.scups.crud.employee.aplication.port.EmployeeServicePort;
import com.scups.crud.employee.domain.Employee;
import com.scups.crud.employee.infrastructure.repository.EmployeeRepositoryJpa;
import com.scups.crud.security.aplication.port.RolServicePort;
import com.scups.crud.security.domain.Rol;
import com.scups.crud.security.domain.User;
import com.scups.crud.security.infrastructure.repository.RolRepository;
import com.scups.crud.security.infrastructure.repository.UserRepositoryJpa;
import com.scups.crud.shared.enums.RolName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.Optional;
import java.util.Set;

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
