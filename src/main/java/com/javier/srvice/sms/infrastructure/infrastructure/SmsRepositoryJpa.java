package com.javier.srvice.sms.infrastructure.infrastructure;

import com.javier.srvice.sms.domain.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsRepositoryJpa extends JpaRepository<Sms, Integer> {
    Sms findByPhoneNumber(String phoneNumber);
}
