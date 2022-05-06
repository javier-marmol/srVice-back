package com.javier.srvice.employee.application;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.application.port.EmployeeServicePort;
import com.javier.srvice.employee.infrastructure.repository.EmployeeRepositoryJpa;
import com.javier.srvice.security.aplication.port.RolServicePort;
import com.javier.srvice.security.domain.User;
import com.javier.srvice.security.infrastructure.repository.UserRepositoryJpa;
import com.javier.srvice.shared.util.AuthUtil;
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
    public Employee create(Employee employee, Integer idUser, String emailAuth) throws Exception {
        User user = userRepositoryJpa.findById(idUser).get();
        AuthUtil.checkAuth(user, emailAuth);
        employee.setUser(user);
        Employee employeeToReturn = employeeRepositoryJpa.save(employee);
        return employeeToReturn;
    }
}
