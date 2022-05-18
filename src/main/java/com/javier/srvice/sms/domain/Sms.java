package com.javier.srvice.sms.domain;

import com.javier.srvice.sms.infrastructure.controller.dto.input.SmsInputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "sms", schema="myschema")
public class Sms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "message")
    private String message;

    @Column(name = "expiration")
    private Date expiration;

    @Column(name = "sending_date")
    private Date sendingDate;

    @Column(name = "code")
    private String code;

    @Column(name = "prefix")
    private String prefix;

    public Sms(SmsInputDto smsInputDto){
        this.setPhoneNumber(smsInputDto.getPhoneNumber());
        this.setPrefix(smsInputDto.getPrefix());
    }
}
