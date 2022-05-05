package com.javier.srvice.security.infrastructure.controller;

import com.javier.srvice.client.domain.Client;
import com.javier.srvice.client.infrastructure.repository.ClientRepositoryJpa;
import com.javier.srvice.security.domain.Rol;
import com.javier.srvice.shared.dto.Alert;
import com.javier.srvice.shared.enums.RolName;
import com.javier.srvice.security.aplication.port.RolServicePort;
import com.javier.srvice.security.aplication.port.UserServicePort;
import com.javier.srvice.security.infrastructure.controller.dto.auth.JwtDto;
import com.javier.srvice.security.infrastructure.controller.dto.auth.LoginDto;
import com.javier.srvice.security.domain.User;
import com.javier.srvice.security.jwt.JwtProvider;
import com.javier.srvice.security.infrastructure.controller.dto.auth.RegisterDto;
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
    UserServicePort userService;

    @Autowired
    RolServicePort rolService;

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
        userService.create(usuario);
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
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getEmail(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}
