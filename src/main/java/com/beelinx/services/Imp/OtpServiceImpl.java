package com.beelinx.services.Imp;

import com.beelinx.config.TwilioConfig;
import com.beelinx.model.UserEntity;
import com.beelinx.utility.EmailUtil;
import com.beelinx.utility.EncryptionOTP;
import com.beelinx.repository.jpa.UserRepository;
import com.beelinx.repository.spec.UserSpecification;
import com.beelinx.services.OtpService;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OtpServiceImpl implements OtpService {

    @Autowired
    TwilioConfig twilioConfig;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserSpecification userSpecification;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private EmailUtil emailUtil;


    @Override
    public void sendMobileOtp(String mobileNumber) throws Exception {
        UserEntity user = userRepository.findByMobileNumber(mobileNumber);
        if (user == null) {
            throw new Exception("User not found for mobile number: " + mobileNumber);
        }
        String otp = generateOtp();
        System.out.println("Generated OTP: " + otp);

        String encryptedOtp = EncryptionOTP.encryptOtp(otp);
        System.out.println("Encrypted OTP: " + encryptedOtp);
        try {
            user.setMobileOtp(encryptedOtp);
            user.setForMobileOtp(LocalDateTime.now());
            userRepository.save(user);
            sendOtp(mobileNumber, otp);
        } catch (Exception e) {
            System.err.println("Error while sending OTP: " + e.getMessage());
            e.getMessage();
        }
    }
    /**
     * Author: Prince Kumar
     * This method verifies the OTP sent via SMS for the provided mobile number.
     * @param mobileNumber The mobile number to verify the OTP against.
     * @param otp The OTP code to verify
     */

    public boolean verifyMobile(String mobileNumber, String otp) throws Exception {
        UserEntity userEntity = userRepository.findByMobileNumber(mobileNumber);

        if (userEntity == null) {
            throw new Exception("User not found for mobile number: " + mobileNumber);
        }

        try {
            String decryptedOtp = EncryptionOTP.decrypt(userEntity.getMobileOtp());

            if (decryptedOtp.equals(otp) &&
                    Duration.between(userEntity.getForMobileOtp(),
                            LocalDateTime.now()).getSeconds() < (10 * 60)) {

                // Set mobile OTP as verified
                userEntity.setMobileOtpVerified(true);

                // Check if both email and mobile are verified
                if (userEntity.isEmailOtpVerified()) {
                    userEntity.setActive(true);
                }

                userRepository.save(userEntity);
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting OTP", e);
        }

        return false;
    }

    /**
     * Author: Prince Kumar
     * This method sent the OTP via EMAIL for the provided email id.
     * The OTP code to verify
     */

    @Override
    public void sendEmailOtp(String email) throws Exception {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new Exception("User email address not Found: " + email);
        }
        String otp = generateOtp();
        System.out.println("Generated OTP: " + otp);
        try {
            String encryptedOtp = EncryptionOTP.encryptOtp(otp);
            userEntity.setEmailOtp(encryptedOtp);
            userEntity.setForEmailOtp(LocalDateTime.now());
            userRepository.save(userEntity);
            // Send email
            emailUtil.sendOtpEmail(email, otp);
        } catch (Exception e) {
            throw new RuntimeException("Error while sending OTP Email", e);
        }
    }

    public boolean verifyEmail(String email, String otp) throws Exception {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new Exception("User not found for this email: " + email);
        }
        try {
            String decryptedOtp = EncryptionOTP.decrypt(userEntity.getEmailOtp());

            if (decryptedOtp.equals(otp) &&
                    Duration.between(userEntity.getForEmailOtp(),
                            LocalDateTime.now()).getSeconds() < (10 * 60)) {

                // Set email OTP as verified
                userEntity.setEmailOtpVerified(true);

                // Check if both email and mobile are verified
                if (userEntity.isMobileOtpVerified()) {
                    userEntity.setActive(true);
                }
                userRepository.save(userEntity);
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting OTP", e);
        }
        return false;
    }

    private void sendOtp(String mobileNumber, String otp) {
        PhoneNumber recipientPhoneNumber = new PhoneNumber(mobileNumber);
        PhoneNumber senderPhoneNumber = new PhoneNumber(twilioConfig.getPhoneNumber());

        String msgBody = "Your One Time Password (OTP) is " + otp;
        Message.creator(recipientPhoneNumber, senderPhoneNumber, msgBody).create();
    }

    public String generateOtp() {
        int otp = (int) (Math.random() * 1000000);
        return String.format("%06d", otp);
    }
}
