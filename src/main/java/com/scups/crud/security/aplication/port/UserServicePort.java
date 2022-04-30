package com.scups.crud.security.aplication.port;

import com.scups.crud.security.domain.User;

import java.util.Optional;

public interface UserServicePort {
    Optional<User> getByNombreUsuario(String name);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    void create(User user);

    User getInfo(Integer idUser);

    void makeUserEmployee(User user);
}
