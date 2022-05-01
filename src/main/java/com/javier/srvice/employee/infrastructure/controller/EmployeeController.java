package com.javier.srvice.employee.infrastructure.controller;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.infrastructure.controller.dto.input.EmployeeInputDto;
import com.javier.srvice.employee.infrastructure.controller.dto.output.EmployeeOutputDto;
import com.javier.srvice.security.aplication.port.UserServicePort;
import com.javier.srvice.employee.aplication.port.EmployeeServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeServicePort employeeServicePort;

    @Autowired
    private UserServicePort userServicePort;

    @PostMapping("create")
    public EmployeeOutputDto create(@RequestBody EmployeeInputDto employeeInputDto){
        Employee employee = new Employee(employeeInputDto);
        Employee employeeSaved = employeeServicePort.create(employee, employeeInputDto.getIdUser());
        EmployeeOutputDto employeeOutputDto = new EmployeeOutputDto(employeeSaved);
        userServicePort.makeUserEmployee(employeeSaved.getUser());
        return employeeOutputDto;
    }
}
