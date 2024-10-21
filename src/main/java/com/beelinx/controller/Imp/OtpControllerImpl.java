package com.beelinx.controller.Imp;

import com.beelinx.helper.ApiResponseMessage;
import com.beelinx.helper.OtpResponse;
import com.beelinx.repository.jpa.UserRepository;
import com.beelinx.services.OtpService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/api/v1/otp")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OtpControllerImpl {

    final OtpService otpService;

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

    @GetMapping("/verify-sms-otp")
    public ResponseEntity<?> verifySmsOtp(@RequestParam String mobileNumber, @RequestParam String otp) {
        try {
            boolean isOtpValid = otpService.verifyMobile(mobileNumber, otp);

            if (isOtpValid) {
                OtpResponse response = new OtpResponse("Mobile Verified Successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                OtpResponse response = new OtpResponse("OTP Validation Expired, Please try again!");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (RuntimeException e) {
            OtpResponse response = new OtpResponse(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            OtpResponse response = new OtpResponse("Mobile is already Verified");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/send-email-otp")
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

    @GetMapping("/verify-email-otp")
    public ResponseEntity<?> verifyEmail(@RequestParam String email, @RequestParam String otp) {
        try {
            boolean isOtpValid = otpService.verifyEmail(email, otp);
            if (isOtpValid) {
                OtpResponse response = new OtpResponse("Email Verified Successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                OtpResponse response = new OtpResponse("OTP Validation Expired, Please try again!");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (RuntimeException e) {
            OtpResponse response = new OtpResponse(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            OtpResponse response = new OtpResponse("Email is already Verified");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}