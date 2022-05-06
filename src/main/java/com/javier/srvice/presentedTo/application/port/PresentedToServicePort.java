package com.javier.srvice.presentedTo.application.port;

import com.javier.srvice.presentedTo.domain.PresentedTo;
import com.javier.srvice.presentedTo.infrastructure.controller.dto.input.PresentedToInputDto;

public interface PresentedToServicePort {
    PresentedTo presentTo(PresentedTo presentedTo, Integer idEmployee, Integer idJob, String emailAuth) throws Exception;
    PresentedTo selectCandidate(Integer idJob, Integer idEmployee, String emailAuth) throws Exception;
    PresentedTo favourite(Integer idJob, Integer idEmployee, String emailAuth) throws Exception;
    PresentedTo deselectFavourite(Integer idJob, Integer idEmployee, String emailAuth) throws Exception;
}
