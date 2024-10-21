package com.beelinx;

import com.beelinx.config.TwilioConfig;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeelinxApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeelinxApplication.class, args);
	}
}