package com.javier.srvice.sms.infrastructure.controller.dto.output;

import com.javier.srvice.sms.domain.Sms;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SmsOutputDto {
    private Integer id;
    private String phoneNumber;
    private Date expiration;

    public SmsOutputDto(Sms sms){
        this.setId(sms.getId());
        this.setPhoneNumber(sms.getPhoneNumber());
        this.setExpiration(sms.getExpiration());
    }
}
