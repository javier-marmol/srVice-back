package com.javier.srvice.security.aplication;

import com.javier.srvice.security.domain.Rol;
import com.javier.srvice.shared.enums.RolName;
import com.javier.srvice.security.aplication.port.RolServicePort;
import com.javier.srvice.security.infrastructure.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolService implements RolServicePort {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolName(RolName rolName){
        return rolRepository.findByRolName(rolName);
    }

    public void save(Rol rol){
        rolRepository.save(rol);
    }
}
