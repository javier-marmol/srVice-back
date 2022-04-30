package com.scups.crud.client.infrastructure.controller;

import com.scups.crud.client.domain.Client;
import com.scups.crud.client.infrastructure.repository.ClientRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin
public class ClientController {

    @Autowired
    private ClientRepositoryJpa clientRepositoryJpa;

    @PostMapping("/getAll")
    private List<Client> getAllClients(){
        return clientRepositoryJpa.findAll();
    }

}
