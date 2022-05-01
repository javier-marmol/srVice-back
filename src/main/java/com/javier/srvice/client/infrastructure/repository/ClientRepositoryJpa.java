package com.javier.srvice.client.infrastructure.repository;

import com.javier.srvice.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepositoryJpa extends JpaRepository<Client, Integer> {
}
