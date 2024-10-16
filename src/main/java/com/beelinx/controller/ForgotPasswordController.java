package com.beelinx.controller;


import com.beelinx.services.ForgotPasswordService;
import com.beelinx.utility.ApiResponse;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/beelinxUser")
public class ForgotPasswordController {
    static Logger log = LoggerFactory.getLogger(ForgotPasswordController.class);

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @GetMapping("/forgotPassword")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestParam("email") String email) throws MessagingException {
        log.info("Email : {}", email);
        String result = forgotPasswordService.forgotPassword(email);
        if (result.equals("Success")) {
            ApiResponse response = new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK,"Password reset email sent successfully.");
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.OK, result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @GetMapping("/setNewPassword")
    public ResponseEntity<ApiResponse> setNewPassword(@RequestParam String email, @RequestParam String newPassword) {
        log.info("Email: {}, NewPassword: {}", email, newPassword);
        String result = forgotPasswordService.setNewPassword(email, newPassword);

        if (result.equals("Success")) {
            ApiResponse response = new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK, "Reset password successfully.");
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }



}
