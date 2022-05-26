package com.javier.srvice.client.application.port;

import com.javier.srvice.client.domain.Client;

public interface ClientServicePort {
    Client getLoggedClient(String emailAuth) throws Exception;
    Client getById(Integer idClient) throws Exception;
}
