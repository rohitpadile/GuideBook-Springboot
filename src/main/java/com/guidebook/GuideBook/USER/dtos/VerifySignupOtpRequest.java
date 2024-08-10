package com.guidebook.GuideBook.USER.dtos;

import lombok.Data;

@Data
public class VerifySignupOtpRequest {
    private String userEmail;
    private String signupOtp;
}
