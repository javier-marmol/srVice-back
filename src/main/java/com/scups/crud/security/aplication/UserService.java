package com.scups.crud.security.aplication;

import com.scups.crud.security.infrastructure.repository.UserRepositoryJpa;
import com.scups.crud.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepositoryJpa userRepositoryJpa;

    public Optional<User> getByNombreUsuario(String name){
        return userRepositoryJpa.findByName(name);
    }

    public boolean existsByName(String name){
        return userRepositoryJpa.existsByName(name);
    }

    public boolean existsByEmail(String email){
        return userRepositoryJpa.existsByEmail(email);
    }

    public void save(User user){
        userRepositoryJpa.save(user);
    }
}
