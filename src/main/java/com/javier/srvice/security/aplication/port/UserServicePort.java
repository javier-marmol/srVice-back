package com.javier.srvice.security.aplication.port;

import com.javier.srvice.security.domain.User;

import java.util.Optional;

public interface UserServicePort {
    Optional<User> getByEmail(String email);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    void create(User user);

    User getInfo(Integer idUser);

    void makeUserEmployee(User user);

    User setProfileImage(Integer idFile, String emailAuth) throws Exception;

    User verificate(String email, String code) throws Exception;

    User getByEmployee(Integer idEmployee) throws Exception;

    User getByClient(Integer idClient) throws Exception;
}
