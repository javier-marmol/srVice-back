package com.javier.srvice.file.infrastructure.repository;

import com.javier.srvice.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepositoryJpa extends JpaRepository<File, Integer> {
}
