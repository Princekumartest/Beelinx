package com.beelinx.services;


import com.beelinx.model.OAuthEntity;
import com.beelinx.model.UserEntity;
import com.beelinx.repository.jpa.OAuthRepository;
import com.beelinx.repository.jpa.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class ForgotPasswordService {

    static Logger log = LoggerFactory.getLogger(ForgotPasswordService.class);
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private OAuthRepository oAuthRepository;

    @Autowired
    private UserRepository userRepository;

    public String forgotPassword(String email) throws MessagingException {
        log.info("Inside forgotPassword");
        OAuthEntity resOAuthEmail=oAuthRepository.findByEmail(email);
        UserEntity resUserEmail=userRepository.findByEmail(email);
        log.info("resUserEmail {} ",resUserEmail);
        log.info("resOAuthEmail {}",resOAuthEmail);
        if (resOAuthEmail != null || resUserEmail !=null)
            return sendRestPasswordEmail(email);
        else
            return "Email id does not exist" ;
    }

    private String sendRestPasswordEmail(String email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        log.info("insert sendRestPasswordEmail ");
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setSubject("Reset Your Account Password");
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
            javaMailSender.send(message);
            log.info("Reset password email sent successfully.");
            return  "Success";
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private String extractNameFromEmail(String email) {
        String namePart = email.split("@")[0]; // Get the part before '@'
        String[] nameParts = namePart.split("\\."); // Split by '.'
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
        log.info("inside setNewPassword");
        OAuthEntity resOAuthEmail = oAuthRepository.findByEmail(email);
        UserEntity resUserEmail=userRepository.findByEmail(email);
        log.info("resUserEmail {} ",resUserEmail);
        log.info("resOAuthEmail {}",resOAuthEmail);
        if(resOAuthEmail != null && resUserEmail !=null){
            resOAuthEmail.setPassword(newPassword);
            oAuthRepository.save(resOAuthEmail);
            resUserEmail.setPassword(newPassword);
            userRepository.save(resUserEmail);
            return "Success";
        }
        else if (resOAuthEmail != null){
            resOAuthEmail.setPassword(newPassword);
            oAuthRepository.save(resOAuthEmail);
            return "Success"; }
        else if (resUserEmail !=null){
            resUserEmail.setPassword(newPassword);
            userRepository.save(resUserEmail);
            return "Success";
        }
        else {
            return "Email id does not exist" ;

        }
    }

}