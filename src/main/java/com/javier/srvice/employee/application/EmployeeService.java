package com.javier.srvice.employee.application;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.application.port.EmployeeServicePort;
import com.javier.srvice.employee.infrastructure.controller.dto.input.EmployeeInputDto;
import com.javier.srvice.employee.infrastructure.controller.dto.output.EmployeeOutputDto;
import com.javier.srvice.employee.infrastructure.repository.EmployeeRepositoryJpa;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.presentedTo.domain.PresentedTo;
import com.javier.srvice.security.aplication.port.RolServicePort;
import com.javier.srvice.security.domain.User;
import com.javier.srvice.security.infrastructure.repository.UserRepositoryJpa;
import com.javier.srvice.shared.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Employee getLoggedEmployee(String emailAuth) throws Exception {
        User user = userRepositoryJpa.findByEmail(emailAuth).orElseThrow(()->new Exception("That user does not exists"));
        Employee employee = employeeRepositoryJpa.findByUser(user).orElseThrow(()->new Exception("That user looks like is not an employee"));
        return employee;
    }

    @Override
    public Employee updateCity(String city, String emailAuth) throws Exception {
        User user = userRepositoryJpa.findByEmail(emailAuth).orElseThrow(() -> new Exception("That user does not exists"));
        Employee employee = employeeRepositoryJpa.findByUser(user).orElse(null);
        if(employee==null) throw new Exception("You are not an employee");
        employee.setCity(city);
        employeeRepositoryJpa.save(employee);
        return employee;
    }

    @Override
    public List<Job> getJobsPresentedTo(String emailAuth) throws Exception {
        User user = userRepositoryJpa.findByEmail(emailAuth).orElseThrow(() -> new Exception("That user does not exists"));
        Employee employee = employeeRepositoryJpa.findByUser(user).orElseThrow(() -> new Exception("That user is not an employee"));
        List<Job> jobs = employee.getJobsPresentedTo().stream().map(PresentedTo::getJob).collect(Collectors.toList());
        return jobs;
    }
}
