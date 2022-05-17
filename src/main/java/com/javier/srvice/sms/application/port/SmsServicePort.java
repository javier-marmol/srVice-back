package com.javier.srvice.sms.application.port;

import com.javier.srvice.sms.domain.Sms;

public interface SmsServicePort {
    Sms sendSms(SmsInputDto smsInputDto) throws Exception;
}
