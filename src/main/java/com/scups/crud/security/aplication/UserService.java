package com.scups.crud.security.aplication;

import com.scups.crud.security.aplication.port.RolServicePort;
import com.scups.crud.security.aplication.port.UserServicePort;
import com.scups.crud.security.domain.Rol;
import com.scups.crud.security.infrastructure.repository.UserRepositoryJpa;
import com.scups.crud.security.domain.User;
import com.scups.crud.shared.enums.RolName;
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

    public Optional<User> getByNombreUsuario(String name){
        return userRepositoryJpa.findByName(name);
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
