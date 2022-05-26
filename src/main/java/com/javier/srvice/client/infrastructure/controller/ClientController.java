package com.javier.srvice.client.infrastructure.controller;

import com.javier.srvice.client.application.ClientService;
import com.javier.srvice.client.application.port.ClientServicePort;
import com.javier.srvice.client.domain.Client;
import com.javier.srvice.client.infrastructure.controller.dto.ClientOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/client")
@CrossOrigin
public class ClientController {

    @Autowired
    private ClientServicePort clientServicePort;

    @GetMapping("getLoggedClient")
    public ClientOutputDto getLoggedClient(Principal principal) throws Exception {
        Client client = clientServicePort.getLoggedClient(principal.getName());
        return new ClientOutputDto(client);
    }    @GetMapping("getById/{idClient}")
    public ClientOutputDto getById(@PathVariable Integer idClient) throws Exception {
        Client client = clientServicePort.getById(idClient);
        return new ClientOutputDto(client);
    }
}
