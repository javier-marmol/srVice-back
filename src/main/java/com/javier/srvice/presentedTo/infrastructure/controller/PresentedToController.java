package com.javier.srvice.presentedTo.infrastructure.controller;

import com.javier.srvice.presentedTo.application.port.PresentedToServicePort;
import com.javier.srvice.presentedTo.domain.PresentedTo;
import com.javier.srvice.presentedTo.infrastructure.controller.dto.input.PresentedToInputDto;
import com.javier.srvice.presentedTo.infrastructure.controller.dto.output.PresentedToOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/presentedTo")
@CrossOrigin
public class PresentedToController {

    @Autowired
    private PresentedToServicePort presentedToServicePort;

    @PostMapping("/presentTo")
    public PresentedToOutputDto present(@RequestBody PresentedToInputDto presentedToInputDto, Principal principal) throws Exception {
        PresentedTo presentedTo = presentedToServicePort.presentTo(presentedToInputDto, principal.getName());
        return new PresentedToOutputDto(presentedTo);
    }
    @GetMapping("/{idJob}")
    public List<PresentedToOutputDto> getByJob(@PathVariable("idJob") Integer idJob) throws Exception {
        List<PresentedTo> presentedTos = presentedToServicePort.getByJob(idJob);
        return presentedTos.stream().map(PresentedToOutputDto::new).collect(Collectors.toList());
    }
    @PutMapping("/{idJob}/selectCandidate/{idEmployee}")
    public PresentedToOutputDto selectCandidate(@PathVariable("idJob") Integer idJob, @PathVariable("idEmployee") Integer idEmployee, Principal principal) throws Exception {
        PresentedTo presentedTo = presentedToServicePort.selectCandidate(idJob, idEmployee, principal.getName());
        return new PresentedToOutputDto(presentedTo);
    }
    @PutMapping("/{idJob}/favourite/{idEmployee}")
    public PresentedToOutputDto favourite(@PathVariable("idJob") Integer idJob, @PathVariable("idEmployee") Integer idEmployee, Principal principal) throws Exception {
        PresentedTo presentedTo = presentedToServicePort.favourite(idJob, idEmployee, principal.getName());
        return new PresentedToOutputDto(presentedTo);
    }
    @PutMapping("/{idJob}/deselectFavourite/{idEmployee}")
    public PresentedToOutputDto deselectFavourite(@PathVariable("idJob") Integer idJob, @PathVariable("idEmployee") Integer idEmployee, Principal principal) throws Exception {
        PresentedTo presentedTo = presentedToServicePort.deselectFavourite(idJob, idEmployee, principal.getName());
        return new PresentedToOutputDto(presentedTo);
    }
}
