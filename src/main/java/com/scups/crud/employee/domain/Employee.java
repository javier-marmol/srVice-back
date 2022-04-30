package com.scups.crud.employee.domain;

import com.scups.crud.employee.infrastructure.controller.dto.input.EmployeeInputDto;
import com.scups.crud.security.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="cif")
    private String cif;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    public Employee(EmployeeInputDto employeeInputDto){
        this.setCif(cif);
    }
}
