package com.javier.srvice.client.application;

import com.javier.srvice.client.application.port.ClientServicePort;
import com.javier.srvice.client.domain.Client;
import com.javier.srvice.client.infrastructure.repository.ClientRepositoryJpa;
import com.javier.srvice.security.domain.User;
import com.javier.srvice.security.infrastructure.repository.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements ClientServicePort {

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private ClientRepositoryJpa clientRepositoryJpa;

    @Override
    public Client getLoggedClient(String emailAuth) throws Exception {
        User user = userRepositoryJpa.findByEmail(emailAuth).orElseThrow(() -> new Exception("That user does not exists"));
        Client client = clientRepositoryJpa.findByUser(user).orElseThrow(() -> new Exception("That client does not exists"));
        return client;
    }

    @Override
    public Client getById(Integer idClient) throws Exception {
        return clientRepositoryJpa.findById(idClient).orElseThrow(() ->new Exception("That client does not exists"));
    }
}
