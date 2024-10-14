package com.beelinx.services;

import com.beelinx.controller.ForgotPasswordController;
import com.beelinx.model.OAuthEntity;
import com.beelinx.repository.jpa.OAuthRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ForgotPasswordService {

    static Logger log = LoggerFactory.getLogger(ForgotPasswordService.class);
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private OAuthRepository oAuthRepository;

    public String forgotPassword(String email) throws MessagingException {
        log.info("Inside forgotPassword");
        sendRestPasswordEmail(email);
        return "successfuly send forgot Password Email";
    }

    private void sendRestPasswordEmail(String email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        log.info("insert sendRestPasswordEmail ");
        try {
            // Use MimeMessageHelper to create the email
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

            // Set the subject
            mimeMessageHelper.setSubject("Reset Your Account Password");

            // Extracting the name from the email
            String userName = extractNameFromEmail(email);
            log.info("userName {}", userName);

            String emailContent = String.format(
                    "Hi %s,<br><br>" +
                            "We received a request to reset your Email password. Please click on the link below to update your password:<br><br>" +
                            "<a href='http://localhost:8081/setNewPassword?email=%s' target='_blank'>" +
                            "<button style='background-color: #20a7db; color: white; padding: 10px 20px; text-decoration: none; border: none; font-size: 14px;'>Reset Password</button></a><br><br>" +
                            "Please ignore this email if you do not wish to reset your password. If you did not request a password reset, please let us know.<br><br>",
                    userName,
                    email
            );


            log.info("emailContent {} ",emailContent);
            mimeMessageHelper.setText(emailContent, true); // true indicates HTML content

            mimeMessageHelper.setTo(email);

            log.info("Ready to send mail ");
            // Send the email
            javaMailSender.send(message);
            log.info("Reset password email sent successfully.");

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private String extractNameFromEmail(String email) {
        // Extract the part before "@" and replace "." with a space
        String namePart = email.split("@")[0]; // Get the part before '@'
        String[] nameParts = namePart.split("\\."); // Split by '.'

        // Capitalize first letter of each part and combine
        StringBuilder nameBuilder = new StringBuilder();
        for (String part : nameParts) {
            if (part.length() > 0) {
                nameBuilder.append(Character.toUpperCase(part.charAt(0))) // Capitalize first letter
                        .append(part.substring(1)) // Add the rest of the part
                        .append(" "); // Add space
            }
        }

        return nameBuilder.toString().trim(); // Trim to remove trailing space

    }

    public String setNewPassword(String email, String newPassword) {
        // Check if the email already exists in the repository
        OAuthEntity existingEntity = oAuthRepository.findByEmail(email);

        // If the email exists, update the password
        if (existingEntity != null) {
            existingEntity.setPassword(newPassword);
            oAuthRepository.save(existingEntity);
            return "Password reset successfully";
        } else {
            // If the email does not exist, create a new entity
            OAuthEntity oAuthEntity = new OAuthEntity();
            oAuthEntity.setEmail(email);
            oAuthEntity.setPassword(newPassword);
            oAuthEntity.setName(extractNameFromEmail(email)); // Extract name from email
            oAuthRepository.save(oAuthEntity);
            return "Account created successfully with new password";
        }
    }

}