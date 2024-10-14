package com.beelinx.controller;


import com.beelinx.services.ForgotPasswordService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
public class ForgotPasswordController {
    static Logger log = LoggerFactory.getLogger(ForgotPasswordController.class);

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @GetMapping("/forgotPassword")
    public String forgotPassword(@RequestParam("email")String email) throws MessagingException {
        log.info("Email : {} ",email);
        return forgotPasswordService.forgotPassword(email);
    }

  @GetMapping("/setNewPassword")
    public String setNewPassword(@RequestParam String email,@RequestHeader String newPassword){
      log.info("Email : {} , NewPassword : {} ",email,newPassword);
    return forgotPasswordService.setNewPassword(email,newPassword);
  }


}
