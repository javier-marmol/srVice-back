package com.javier.srvice.security.aplication.port;

import com.javier.srvice.security.domain.Rol;
import com.javier.srvice.shared.enums.RolName;

import java.util.Optional;

public interface RolServicePort {
    Optional<Rol> getByRolName(RolName rolName);

    void save(Rol rol);
}
