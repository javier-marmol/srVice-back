package com.scups.crud.security.infrastructure.controller;

import com.scups.crud.security.aplication.port.UserServicePort;
import com.scups.crud.security.domain.User;
import com.scups.crud.security.infrastructure.controller.dto.output.UserOutputDto;
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
