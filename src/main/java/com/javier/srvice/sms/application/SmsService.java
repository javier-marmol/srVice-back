package com.javier.srvice.sms.application;

import com.javier.srvice.security.infrastructure.repository.UserRepositoryJpa;
import com.javier.srvice.sms.application.port.SmsServicePort;
import com.javier.srvice.sms.configuration.TwilioConfiguration;
import com.javier.srvice.sms.domain.Sms;
import com.javier.srvice.sms.infrastructure.controller.dto.input.SmsInputDto;
import com.javier.srvice.sms.infrastructure.infrastructure.SmsRepositoryJpa;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.twilio.type.PhoneNumber;


import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SmsService implements SmsServicePort {

    @Autowired
    private SmsRepositoryJpa smsRepositoryJpa;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public SmsService(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public Sms sendSms(SmsInputDto smsInputDto) throws Exception {
        checkCorrectFormat(smsInputDto.getPrefix(), smsInputDto.getPhoneNumber());
        checkIfUserExitsByPhoneNumber(smsInputDto.getPhoneNumber());
        checkIfOldSms(smsInputDto);
        Sms sms = new Sms(smsInputDto);
        Date time = new Date();
        sms.setSendingDate(time);
        time.setMinutes(time.getMinutes()+15);
        sms.setExpiration(time);
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        sendToTwilio(sms, number);
        return smsRepositoryJpa.save(sms);
    }

    private void sendToTwilio(Sms sms, int number) {
        sms.setCode(String.format("%06d", number));
        sms.setMessage("Your verification code is: " + sms.getCode() + " Don't share this with anyone");
        PhoneNumber to = new PhoneNumber(sms.getPrefix()+sms.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
        String message = sms.getMessage();
        MessageCreator creator = Message.creator(to, from, message);
        creator.create();
    }

    private void checkIfOldSms(SmsInputDto smsInputDto) throws Exception {
        Sms oldSms = smsRepositoryJpa.findByPhoneNumber(smsInputDto.getPhoneNumber());
        if(oldSms!=null) {
            Date oldTime = oldSms.getSendingDate();
            oldTime.setMinutes(oldSms.getSendingDate().getMinutes() + 3);
            if (oldTime.compareTo(new Date()) > 0) {
                throw new Exception("Wait at least 3 minutes to send another SMS");
            }
            smsRepositoryJpa.delete(oldSms);
        }
    }

    private void checkCorrectFormat(String prefix, String phoneNumber) throws Exception {
        String regex = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(prefix + " " + phoneNumber);
        if(!matcher.matches()) throw new Exception("That phone number is not well formatted");
    }
    private void checkIfUserExitsByPhoneNumber(String phoneNumber) throws Exception {
        userRepositoryJpa.findByPhoneNumber(phoneNumber).orElseThrow(() -> new Exception("We cannot send an SMS to that number"));
    }
}
