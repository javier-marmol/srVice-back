package com.javier.srvice.presentedTo.domain;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.presentedTo.infrastructure.controller.dto.input.PresentedToInputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "presented_to", schema="public")
public class PresentedTo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job", referencedColumnName = "id")
    private Job job;

    @Column(name = "selected")
    private Boolean selected;

    @Column(name = "favourite")
    private Boolean favourite;

    public PresentedTo(PresentedToInputDto presentedToInputDto){
        this.setFavourite(false);
        this.setSelected(false);
    }
}
