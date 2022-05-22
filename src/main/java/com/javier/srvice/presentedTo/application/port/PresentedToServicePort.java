package com.javier.srvice.presentedTo.application.port;

import com.javier.srvice.presentedTo.domain.PresentedTo;
import com.javier.srvice.presentedTo.infrastructure.controller.dto.input.PresentedToInputDto;

import java.util.List;

public interface PresentedToServicePort {
    PresentedTo presentTo(PresentedToInputDto presentedToInputDto, String emailAuth) throws Exception;
    PresentedTo selectCandidate(Integer idJob, Integer idEmployee, String emailAuth) throws Exception;
    PresentedTo favourite(Integer idJob, Integer idEmployee, String emailAuth) throws Exception;
    PresentedTo deselectFavourite(Integer idJob, Integer idEmployee, String emailAuth) throws Exception;
    List<PresentedTo> getByJob(Integer idJob) throws Exception;
}
