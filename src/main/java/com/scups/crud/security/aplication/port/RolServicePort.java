package com.scups.crud.security.aplication.port;

import com.scups.crud.security.domain.Rol;
import com.scups.crud.shared.enums.RolName;

import java.util.Optional;

public interface RolServicePort {
    Optional<Rol> getByRolName(RolName rolName);

    void save(Rol rol);
}
