package com.scups.crud.employee.aplication.port;

import com.scups.crud.employee.domain.Employee;

public interface EmployeeServicePort {
    Employee create(Employee employee, Integer idUser);
}
