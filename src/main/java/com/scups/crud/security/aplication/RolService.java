package com.scups.crud.security.aplication;

import com.scups.crud.security.aplication.port.RolServicePort;
import com.scups.crud.security.domain.Rol;
import com.scups.crud.shared.enums.RolName;
import com.scups.crud.security.infrastructure.repository.RolRepository;
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
