package com.javier.srvice.sms.application.port;

import com.javier.srvice.sms.domain.Sms;
import com.javier.srvice.sms.infrastructure.controller.dto.input.SmsInputDto;

public interface SmsServicePort {
    Sms sendSms(SmsInputDto smsInputDto) throws Exception;
}
