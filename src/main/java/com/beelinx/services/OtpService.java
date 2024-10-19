package com.beelinx.services;

public interface OtpService {

    void sendMobileOtp(String mobileNumber) throws Exception;

    boolean verifyMobile(String mobileNumber, String otp) throws Exception;

    void sendEmailOtp(String email) throws Exception;

    boolean verifyEmail(String email, String otp) throws Exception;

    String generateOtp();

}
