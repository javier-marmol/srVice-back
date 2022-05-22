package com.javier.srvice.employee.infrastructure.controller;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.infrastructure.controller.dto.input.EmployeeInputDto;
import com.javier.srvice.employee.infrastructure.controller.dto.output.EmployeeOutputDto;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.job.infrastructure.controller.dto.output.JobOutputDto;
import com.javier.srvice.presentedTo.infrastructure.controller.dto.output.PresentedToOutputDto;
import com.javier.srvice.presentedTo.infrastructure.controller.dto.output.SimplePresentedToOutputDto;
import com.javier.srvice.security.aplication.port.UserServicePort;
import com.javier.srvice.employee.application.port.EmployeeServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping("getJobsPresentedTo")
    public List<JobOutputDto> getJobsPresentedTo(Principal principal) throws Exception {
        List<Job> jobs = employeeServicePort.getJobsPresentedTo(principal.getName());
        return jobs.stream().map(JobOutputDto::new).collect(Collectors.toList());
    }

    @GetMapping("getLoggedEmployee")
    public EmployeeOutputDto getLoggedEmployee(Principal principal) throws Exception {
        Employee employee = employeeServicePort.getLoggedEmployee(principal.getName());
        return new EmployeeOutputDto(employee);
    }
    @PutMapping("updateCity/{city}")
    public EmployeeOutputDto updateCity(@PathVariable("city") String city, Principal principal) throws Exception {
        Employee employee = employeeServicePort.updateCity(city, principal.getName());
        return new EmployeeOutputDto(employee);
    }
}
