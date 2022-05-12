package com.javier.srvice.security.aplication;

import com.javier.srvice.security.aplication.port.RolServicePort;
import com.javier.srvice.security.aplication.port.UserServicePort;
import com.javier.srvice.security.domain.Rol;
import com.javier.srvice.security.domain.User;
import com.javier.srvice.security.infrastructure.repository.UserRepositoryJpa;
import com.javier.srvice.shared.enums.RolName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService implements UserServicePort {

    @Autowired
    UserRepositoryJpa userRepositoryJpa;

    @Autowired
    RolServicePort rolServicePort;

    public Optional<User> getByEmail(String email){
        return userRepositoryJpa.findByEmail(email);
    }

    public boolean existsByName(String name){
        return userRepositoryJpa.existsByName(name);
    }

    public boolean existsByEmail(String email){
        return userRepositoryJpa.existsByEmail(email);
    }

    public void create(User user){
        userRepositoryJpa.save(user);
    }

    public User getInfo(Integer idUser) {
        return userRepositoryJpa.findById(idUser).get();
    }

    public void makeUserEmployee(User user) {
        Set<Rol> roles = user.getRols();
        Rol rol = rolServicePort.getByRolName(RolName.ROLE_EMPLOYEE).get();
        roles.add(rol);
        user.setRols(roles);
        create(user);
    }
}

