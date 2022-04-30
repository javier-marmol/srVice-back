package com.scups.crud.security.infrastructure.controller;

import com.scups.crud.client.domain.Client;
import com.scups.crud.client.infrastructure.repository.ClientRepositoryJpa;
import com.scups.crud.shared.dto.Alert;
import com.scups.crud.security.infrastructure.controller.dto.JwtDto;
import com.scups.crud.security.infrastructure.controller.dto.LoginDto;
import com.scups.crud.security.domain.Rol;
import com.scups.crud.security.domain.User;
import com.scups.crud.shared.enums.RolName;
import com.scups.crud.security.jwt.JwtProvider;
import com.scups.crud.security.aplication.RolService;
import com.scups.crud.security.aplication.UserService;
import com.scups.crud.security.infrastructure.controller.dto.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    ClientRepositoryJpa clientRepositoryJpa;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Alert("Wrong data"), HttpStatus.BAD_REQUEST);
        if(userService.existsByName(registerDto.getName()))
            return new ResponseEntity(new Alert("That name is already in use"), HttpStatus.BAD_REQUEST);
        if(userService.existsByEmail(registerDto.getEmail()))
            return new ResponseEntity(new Alert("That email is already in use"), HttpStatus.BAD_REQUEST);
        User usuario =
                new User(registerDto.getName(), registerDto.getEmail(),
                        passwordEncoder.encode(registerDto.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolName(RolName.ROLE_CLIENT).get());
        if(registerDto.getRoles().contains("admin"))
            roles.add(rolService.getByRolName(RolName.ROLE_ADMIN).get());
        usuario.setRols(roles);
        userService.save(usuario);
        Client client = new Client();
        client.setUser(usuario);
        clientRepositoryJpa.save(client);
        return new ResponseEntity(new Alert("saved"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginDto loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Alert("Wrong data"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getName(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
    @GetMapping("/register/google")
    public String registerGoogle(){
        return "Todo cool";
    }
}
