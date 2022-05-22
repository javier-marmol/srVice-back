package com.javier.srvice.employee.application.port;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.infrastructure.controller.dto.input.EmployeeInputDto;
import com.javier.srvice.employee.infrastructure.controller.dto.output.EmployeeOutputDto;

public interface EmployeeServicePort {
    Employee create(EmployeeInputDto employeeInputDto, String emailAuth) throws Exception;
    Employee getLoggedEmployee(String emailAuth) throws Exception;
    Employee updateCity(String city, String emailAuth) throws Exception;
}
