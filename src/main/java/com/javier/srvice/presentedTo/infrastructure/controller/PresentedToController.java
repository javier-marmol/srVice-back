package com.javier.srvice.presentedTo.infrastructure.controller;

import com.javier.srvice.presentedTo.application.port.PresentedToServicePort;
import com.javier.srvice.presentedTo.domain.PresentedTo;
import com.javier.srvice.presentedTo.infrastructure.controller.dto.input.PresentedToInputDto;
import com.javier.srvice.presentedTo.infrastructure.controller.dto.output.PresentedToOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/presentedTo")
@CrossOrigin
public class PresentedToController {

    @Autowired
    private PresentedToServicePort presentedToServicePort;

    @PostMapping("/presentTo")
    public PresentedToOutputDto present(@RequestBody PresentedToInputDto presentedToInputDto) throws Exception {
        PresentedTo presentedTo = new PresentedTo(presentedToInputDto);
        presentedTo = presentedToServicePort.presentTo(presentedTo, presentedToInputDto.getIdEmployee(), presentedToInputDto.getIdJob());
        return new PresentedToOutputDto(presentedTo);
    }
}
