package com.guidebook.GuideBook.USER.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class sendOtpToSignupEmailRequest {
    @NotNull(message = "Email cannot be null when signing up!")
    private String userEmail;
}
