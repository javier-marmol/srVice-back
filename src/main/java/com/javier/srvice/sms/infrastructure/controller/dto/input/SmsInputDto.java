package com.javier.srvice.sms.infrastructure.controller.dto.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsInputDto {
    private String phoneNumber;
    private String prefix;
}
