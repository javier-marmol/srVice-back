package com.javier.srvice.presentedTo.infrastructure.controller.dto.output;

import com.javier.srvice.presentedTo.domain.PresentedTo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimplePresentedToOutputDto {
    private Integer id;
    private Integer idEmployee;
    private Integer idJob;

    public SimplePresentedToOutputDto(PresentedTo presentedTo){
        this.setId(presentedTo.getId());
        this.setIdEmployee(presentedTo.getEmployee().getId());
        this.setIdJob(presentedTo.getJob().getId());
    }
}
