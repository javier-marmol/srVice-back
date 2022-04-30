package com.scups.crud.security.infrastructure.repository;

import com.scups.crud.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);
    boolean existsByName(String name);
    boolean existsByEmail(String email);

}
