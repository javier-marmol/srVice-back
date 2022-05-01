package com.javier.srvice.security.infrastructure.controller;

import com.javier.srvice.security.infrastructure.controller.dto.output.UserOutputDto;
import com.javier.srvice.security.aplication.port.UserServicePort;
import com.javier.srvice.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserServicePort userServicePort;

    @GetMapping("{id}")
    public UserOutputDto getUserInfo(@PathVariable("id") Integer id){
        User user = userServicePort.getInfo(id);
        return new UserOutputDto(user);
    }
}
