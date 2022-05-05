package com.javier.srvice.presentedTo.application.port;

import com.javier.srvice.presentedTo.domain.PresentedTo;
import com.javier.srvice.presentedTo.infrastructure.controller.dto.input.PresentedToInputDto;

public interface PresentedToServicePort {
    PresentedTo presentTo(PresentedTo presentedTo, Integer idEmployee, Integer idJob) throws Exception;

}
