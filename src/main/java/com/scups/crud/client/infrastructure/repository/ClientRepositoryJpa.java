package com.scups.crud.client.infrastructure.repository;

import com.scups.crud.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepositoryJpa extends JpaRepository<Client, Integer> {
}
