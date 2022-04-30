package com.scups.crud.employee.infrastructure.controller;

import com.scups.crud.employee.aplication.port.EmployeeServicePort;
import com.scups.crud.employee.domain.Employee;
import com.scups.crud.employee.infrastructure.controller.dto.input.EmployeeInputDto;
import com.scups.crud.employee.infrastructure.controller.dto.output.EmployeeOutputDto;
import com.scups.crud.security.aplication.port.UserServicePort;
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
