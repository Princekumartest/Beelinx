package com.beelinx.controller.Imp;

import com.beelinx.helper.ApiResponseMessage;
import com.beelinx.helper.OtpResponse;
import com.beelinx.repository.jpa.UserRepository;
import com.beelinx.services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/api/v1/otp")
public class OtpControllerImpl {

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/send-sms-otp")
    public ResponseEntity<?> sendMobileOtp(@RequestParam String mobileNumber) throws Exception {

        try{
            otpService.sendMobileOtp(mobileNumber);

            ApiResponseMessage response = new ApiResponseMessage(
                    "OTP sent successfully",
                    "SUCCESS",
                    HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw new ValidationException("Failed to send OTP: " + ex.getMessage());
        }
    }

    @PostMapping("/verify-sms-otp")
    public ResponseEntity<?> verifySmsOtp(@RequestParam String mobileNumber, @RequestParam String otp) {
        try {
            boolean isOtpValid = otpService.verifyMobile(mobileNumber, otp);

            if (isOtpValid) {
                OtpResponse response = new OtpResponse("Mobile OTP validated successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                OtpResponse response = new OtpResponse("OTP validation expired, Please try again!");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (RuntimeException e) {
            OtpResponse response = new OtpResponse(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            OtpResponse response = new OtpResponse("An error occurred");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/send-email-otp")
    public ResponseEntity<?> sendOtpEmail(@RequestParam String email) throws Exception {
        try {
            otpService.sendEmailOtp(email);
            ApiResponseMessage response = new ApiResponseMessage(
                    "OTP sent successfully",
                    "Success",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw new ValidationException("Failed to send OTP: " + ex.getMessage());
        }
    }

    @PostMapping("/verify-email-otp")
    public ResponseEntity<?> verifyEmail(@RequestParam String email, @RequestParam String otp) {
        try {
            boolean isOtpValid = otpService.verifyEmail(email, otp);
            if (isOtpValid) {
                OtpResponse response = new OtpResponse("Email OTP validated successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                OtpResponse response = new OtpResponse("OTP validation expired, Please try again!");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (RuntimeException e) {
            OtpResponse response = new OtpResponse(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            OtpResponse response = new OtpResponse("An error occurred");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}