package com.scups.crud.security.aplication;

import com.scups.crud.security.infrastructure.repository.UsuarioRepository;
import com.scups.crud.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Optional<User> getByNombreUsuario(String name){
        return usuarioRepository.findByName(name);
    }

    public boolean existsByName(String name){
        return usuarioRepository.existsByName(name);
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void save(User user){
        usuarioRepository.save(user);
    }
}
