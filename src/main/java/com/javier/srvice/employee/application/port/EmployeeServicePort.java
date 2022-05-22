package com.javier.srvice.employee.application.port;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.infrastructure.controller.dto.input.EmployeeInputDto;
import com.javier.srvice.employee.infrastructure.controller.dto.output.EmployeeOutputDto;
import com.javier.srvice.job.domain.Job;

import java.util.List;

public interface EmployeeServicePort {
    Employee create(EmployeeInputDto employeeInputDto, String emailAuth) throws Exception;
    Employee getLoggedEmployee(String emailAuth) throws Exception;
    Employee updateCity(String city, String emailAuth) throws Exception;
    List<Job> getJobsPresentedTo(String emailAuth) throws Exception;
}
