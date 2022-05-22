package com.javier.srvice.employee.infrastructure.controller.dto.output;

import com.javier.srvice.employee.domain.Employee;
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
    private String city;

    public EmployeeOutputDto(Employee employee){
        this.setId(employee.getId());
        this.setCif(employee.getCif());
        this.setIdUser(employee.getUser().getId());
        this.setCity(employee.getCity());
    }
}
