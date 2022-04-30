package com.scups.crud.shared.util;

import com.scups.crud.security.aplication.RolService;
import com.scups.crud.security.aplication.UserService;
import com.scups.crud.security.domain.Rol;
import com.scups.crud.security.domain.User;
import com.scups.crud.shared.enums.RolName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Rol rol = new Rol();
        rol.setRolName(RolName.ROLE_CLIENT);
        rolService.save(rol);
    }
}
