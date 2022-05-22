package com.javier.srvice.employee.infrastructure.controller;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.infrastructure.controller.dto.input.EmployeeInputDto;
import com.javier.srvice.employee.infrastructure.controller.dto.output.EmployeeOutputDto;
import com.javier.srvice.security.aplication.port.UserServicePort;
import com.javier.srvice.employee.application.port.EmployeeServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeServicePort employeeServicePort;

    @Autowired
    private UserServicePort userServicePort;

    @PostMapping("create")
    public EmployeeOutputDto create(@RequestBody EmployeeInputDto employeeInputDto, Principal principal) throws Exception {
        Employee employeeSaved = employeeServicePort.create(employeeInputDto, principal.getName());
        EmployeeOutputDto employeeOutputDto = new EmployeeOutputDto(employeeSaved);
        userServicePort.makeUserEmployee(employeeSaved.getUser());
        return employeeOutputDto;
    }

    @GetMapping("getLoggedEmployee")
    public EmployeeOutputDto getLoggedEmployee(Principal principal) throws Exception {
        Employee employee = employeeServicePort.getLoggedEmployee(principal.getName());
        return new EmployeeOutputDto(employee);
    }
}
