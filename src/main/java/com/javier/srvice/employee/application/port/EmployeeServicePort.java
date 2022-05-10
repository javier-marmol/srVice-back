package com.javier.srvice.employee.application.port;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.infrastructure.controller.dto.input.EmployeeInputDto;

public interface EmployeeServicePort {
    Employee create(EmployeeInputDto employeeInputDto, String emailAuth) throws Exception;
}
