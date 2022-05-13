package com.javier.srvice.security.aplication;

import com.javier.srvice.file.application.port.FileStoragePort;
import com.javier.srvice.file.domain.File;
import com.javier.srvice.file.infrastructure.repository.FileRepositoryJpa;
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

    @Autowired
    private FileStoragePort fileStoragePort;

    @Autowired
    private FileRepositoryJpa fileRepositoryJpa;

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

    public User setProfileImage(Integer idFile, String emailAuth) throws Exception {
        User user = userRepositoryJpa.findByEmail(emailAuth).orElseThrow(() -> new Exception("That user does not exists"));
        File file = fileRepositoryJpa.findById(idFile).orElseThrow(() -> new Exception("That file does not exists"));
        if(user.getImage()!=null) fileStoragePort.deleteFile(user.getImage().getId());
        user.setImage(file);
        userRepositoryJpa.save(user);
        return user;
    }
}

