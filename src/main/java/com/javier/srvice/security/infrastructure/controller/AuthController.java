package com.javier.srvice.security.infrastructure.controller;

import com.javier.srvice.client.domain.Client;
import com.javier.srvice.client.infrastructure.repository.ClientRepositoryJpa;
import com.javier.srvice.security.domain.Rol;
import com.javier.srvice.security.domain.UsuarioPrincipal;
import com.javier.srvice.security.infrastructure.controller.dto.output.UserOutputDto;
import com.javier.srvice.shared.dto.Alert;
import com.javier.srvice.shared.enums.RolName;
import com.javier.srvice.security.aplication.port.RolServicePort;
import com.javier.srvice.security.aplication.port.UserServicePort;
import com.javier.srvice.security.infrastructure.controller.dto.auth.JwtDto;
import com.javier.srvice.security.infrastructure.controller.dto.auth.LoginDto;
import com.javier.srvice.security.domain.User;
import com.javier.srvice.security.jwt.JwtProvider;
import com.javier.srvice.security.infrastructure.controller.dto.auth.RegisterDto;
import com.javier.srvice.sms.domain.Sms;
import com.javier.srvice.sms.infrastructure.infrastructure.SmsRepositoryJpa;
import org.apache.commons.io.FilenameUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServicePort userService;

    @Autowired
    private RolServicePort rolService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private ClientRepositoryJpa clientRepositoryJpa;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Alert("Wrong data"), HttpStatus.BAD_REQUEST);
        if(userService.existsByName(registerDto.getName()))
            return new ResponseEntity(new Alert("That name is already in use"), HttpStatus.BAD_REQUEST);
        if(userService.existsByEmail(registerDto.getEmail()))
            return new ResponseEntity(new Alert("That email is already in use"), HttpStatus.BAD_REQUEST);
        User user =
                new User(registerDto.getName(), registerDto.getEmail(),
                        passwordEncoder.encode(registerDto.getPassword()), registerDto.getPhoneNumber());
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolName(RolName.ROLE_CLIENT).get());
        user.setRols(roles);
        user.setVerified(false);
        userService.create(user);
        Client client = new Client();
        client.setUser(user);
        clientRepositoryJpa.save(client);
        return new ResponseEntity(new Alert("saved"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public JwtDto login(@Valid @RequestBody LoginDto loginUsuario, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors())
            throw new Exception("Wrong data");
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getEmail(), loginUsuario.getPassword()));
        UsuarioPrincipal user = (UsuarioPrincipal) authentication.getPrincipal();
        User userToCheck = userService.getByEmail(user.getEmail()).get();
        if(!userToCheck.getVerified()) throw new Exception("You are not verfied");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return jwtDto;
    }

    @PutMapping("/verificate/{code}")
    public JwtDto verificate(@RequestBody LoginDto loginDto, BindingResult bindingResult, @PathVariable(name = "code") String code) throws Exception {
        if(bindingResult.hasErrors())
            throw new Exception("Wrong data");
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        UsuarioPrincipal user = (UsuarioPrincipal) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return jwtDto;
    }
}
