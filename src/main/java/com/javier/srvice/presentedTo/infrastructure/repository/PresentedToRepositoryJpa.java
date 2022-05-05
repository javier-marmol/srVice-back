package com.javier.srvice.presentedTo.infrastructure.repository;

import com.javier.srvice.presentedTo.domain.PresentedTo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresentedToRepositoryJpa extends JpaRepository<PresentedTo, Integer> {
}
