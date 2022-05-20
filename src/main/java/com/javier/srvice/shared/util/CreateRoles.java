package com.javier.srvice.shared.util;

import com.javier.srvice.security.domain.Rol;
import com.javier.srvice.security.aplication.RolService;
import com.javier.srvice.security.aplication.UserService;
import com.javier.srvice.shared.enums.RolName;
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
        if(rolService.getByRolName(RolName.ROLE_CLIENT).isEmpty()) {
            Rol rolClient = new Rol();
            rolClient.setRolName(RolName.ROLE_CLIENT);
            rolService.save(rolClient);
        }
        if(rolService.getByRolName(RolName.ROLE_ADMIN).isEmpty()){
            Rol roldAdmin = new Rol();
            roldAdmin.setRolName(RolName.ROLE_ADMIN);
            rolService.save(roldAdmin);
        }
        if(rolService.getByRolName(RolName.ROLE_EMPLOYEE).isEmpty()) {
            Rol rolEmployee = new Rol();
            rolEmployee.setRolName(RolName.ROLE_EMPLOYEE);
            rolService.save(rolEmployee);
        }
    }
}
