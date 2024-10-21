package com.beelinx.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwilioSmsConfig {

    @Autowired
    private TwilioConfig twilioConfig;

    @PostConstruct
    public void setup() {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

/*    @Value("${twilio.whatsapp.from}")
    private String fromNumber;

    public String getPhoneNumber() {
        return fromNumber; // This should return your Twilio WhatsApp-enabled number
    }*/
}
