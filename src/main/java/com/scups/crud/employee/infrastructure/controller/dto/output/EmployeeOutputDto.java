package com.scups.crud.employee.infrastructure.controller.dto.output;

import com.scups.crud.employee.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class EmployeeOutputDto {
    private Integer id;
    private String cif;
    private Integer idUser;

    public EmployeeOutputDto(Employee employee){
        this.setId(employee.getId());
        this.setCif(employee.getCif());
        this.setIdUser(employee.getUser().getId());
    }
}
