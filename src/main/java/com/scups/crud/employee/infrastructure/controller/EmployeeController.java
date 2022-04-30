package com.scups.crud.employee.infrastructure.controller;

import com.scups.crud.employee.aplication.port.EmployeeServicePort;
import com.scups.crud.employee.domain.Employee;
import com.scups.crud.employee.infrastructure.controller.dto.input.EmployeeInputDto;
import com.scups.crud.employee.infrastructure.controller.dto.output.EmployeeOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeServicePort employeeServicePort;

    @PostMapping("create")
    public EmployeeOutputDto create(@RequestBody EmployeeInputDto employeeInputDto){
        Employee employee = new Employee(employeeInputDto);
        return new EmployeeOutputDto(employeeServicePort.create(employee, employeeInputDto.getIdUser()));
    }
}
