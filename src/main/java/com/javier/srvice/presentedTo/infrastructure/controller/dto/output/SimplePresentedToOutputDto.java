package com.javier.srvice.presentedTo.infrastructure.controller.dto.output;

import com.javier.srvice.presentedTo.domain.PresentedTo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimplePresentedToOutputDto {
    private Integer id;
    private Integer idEmployee;
    private String name;
    private Integer idJob;
    private Integer idUser;

    public SimplePresentedToOutputDto(PresentedTo presentedTo){
        this.setId(presentedTo.getId());
        this.setIdEmployee(presentedTo.getEmployee().getId());
        this.setIdJob(presentedTo.getJob().getId());
        this.setName(presentedTo.getEmployee().getUser().getName());
        this.setIdUser(presentedTo.getEmployee().getUser().getId());
    }
}
