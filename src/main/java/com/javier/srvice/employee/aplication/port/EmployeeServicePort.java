package com.javier.srvice.employee.aplication.port;

import com.javier.srvice.employee.domain.Employee;

public interface EmployeeServicePort {
    Employee create(Employee employee, Integer idUser);
}
