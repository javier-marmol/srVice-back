package com.javier.srvice.security.infrastructure.controller;

import com.javier.srvice.security.infrastructure.controller.dto.output.UserOutputDto;
import com.javier.srvice.security.aplication.port.UserServicePort;
import com.javier.srvice.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.Authenticator;
import java.security.Principal;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserServicePort userServicePort;

    @GetMapping("")
    public UserOutputDto getUserInfo(Principal principal) throws Exception {
        String email = principal.getName();
        User user = userServicePort.getByEmail(email).orElseThrow(() -> new Exception("That user does not exists"));
        return new UserOutputDto(user);
    }
    @PutMapping("setProfileImage/{idFile}")
    public UserOutputDto setProfileImage(@PathVariable("idFile") Integer idFile, Principal principal) throws Exception {
        User user = userServicePort.setProfileImage(idFile, principal.getName());
        return new UserOutputDto(user);
    }
    @GetMapping("getUserByEmployee/{idEmployee}")
    public UserOutputDto getUserByEmployee(@PathVariable("idEmployee") Integer idEmployee) throws Exception {
        User user = userServicePort.getByEmployee(idEmployee);
        return new UserOutputDto(user);
    }
    @GetMapping("getUserByClient/{idClient}")
    public UserOutputDto getUserByClient(@PathVariable("idClient") Integer idClient) throws Exception {
        User user = userServicePort.getByClient(idClient);
        return new UserOutputDto(user);
    }
}
