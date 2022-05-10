package com.javier.srvice.employee.application;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.application.port.EmployeeServicePort;
import com.javier.srvice.employee.infrastructure.controller.dto.input.EmployeeInputDto;
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
    public Employee create(EmployeeInputDto employeeInputDto ,String emailAuth) throws Exception {
        Employee employee = new Employee(employeeInputDto);
        User user = userRepositoryJpa.findById(employeeInputDto.getIdUser()).orElseThrow(() -> new Exception("That user does not exists"));
        AuthUtil.checkAuth(user, emailAuth);
        employee.setUser(user);
        Employee employeeToReturn = employeeRepositoryJpa.save(employee);
        return employeeToReturn;
    }
}
