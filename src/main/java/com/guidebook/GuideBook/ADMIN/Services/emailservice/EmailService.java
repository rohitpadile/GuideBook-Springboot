package com.guidebook.GuideBook.ADMIN.Services.emailservice;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
//    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException;

//    void sendOtp(String phoneNumber, String email);
//    boolean verifyOtp(String phoneNumber, String email, Long otp);
//    long generateOtp();

}
