package com.javier.srvice.client.infrastructure.repository;

import com.javier.srvice.client.domain.Client;
import com.javier.srvice.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepositoryJpa extends JpaRepository<Client, Integer> {
    Optional<Client> findByUser(User user);
}
