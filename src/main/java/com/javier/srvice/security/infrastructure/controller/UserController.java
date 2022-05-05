package com.javier.srvice.security.infrastructure.controller;

import com.javier.srvice.security.infrastructure.controller.dto.output.UserOutputDto;
import com.javier.srvice.security.aplication.port.UserServicePort;
import com.javier.srvice.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
