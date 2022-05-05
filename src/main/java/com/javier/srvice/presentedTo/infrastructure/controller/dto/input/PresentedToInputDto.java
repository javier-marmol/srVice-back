package com.javier.srvice.presentedTo.infrastructure.controller.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PresentedToInputDto {
    private Double suggestedPrice;
    private Integer idEmployee;
    private Integer idJob;
}
