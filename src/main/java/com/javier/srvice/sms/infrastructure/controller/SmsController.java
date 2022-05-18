package com.javier.srvice.sms.infrastructure.controller;

import com.javier.srvice.sms.application.port.SmsServicePort;
import com.javier.srvice.sms.domain.Sms;
import com.javier.srvice.sms.infrastructure.controller.dto.input.SmsInputDto;
import com.javier.srvice.sms.infrastructure.controller.dto.output.SmsOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsServicePort smsService;

    @PostMapping("/send")
    public SmsOutputDto send(@RequestBody()SmsInputDto smsInputDto) throws Exception {
        Sms sms = smsService.sendSms(smsInputDto);
        return new SmsOutputDto(sms);
    }
}
