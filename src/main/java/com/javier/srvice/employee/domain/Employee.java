package com.javier.srvice.employee.domain;

import com.javier.srvice.employee.infrastructure.controller.dto.input.EmployeeInputDto;
import com.javier.srvice.presentedTo.domain.PresentedTo;
import com.javier.srvice.security.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "employee", schema="myschema")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="cif")
    private String cif;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<PresentedTo> jobsPresentedTo;

    public Employee(EmployeeInputDto employeeInputDto){
        this.setCif(employeeInputDto.getCif());
    }
}
