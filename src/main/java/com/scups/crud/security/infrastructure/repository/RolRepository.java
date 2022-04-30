package com.scups.crud.security.infrastructure.repository;

import com.scups.crud.security.domain.Rol;
import com.scups.crud.shared.enums.RolName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolName(RolName rolName);
}
