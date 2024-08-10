package com.guidebook.GuideBook.USER.Service;

import com.guidebook.GuideBook.ADMIN.Services.emailservice.EmailServiceImpl;
import com.guidebook.GuideBook.USER.Models.Otp;
import com.guidebook.GuideBook.USER.Repository.MyUserRepository;
import com.guidebook.GuideBook.USER.Repository.OtpRepository;
import com.guidebook.GuideBook.USER.dtos.VerifySignupOtpRequest;
import com.guidebook.GuideBook.USER.dtos.sendOtpToSignupEmailRequest;
import com.guidebook.GuideBook.USER.exceptions.SignupOtpAlreadyPresentException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class MyUserService {
    private final MyUserRepository myUserRepository;
    private final EmailServiceImpl emailServiceImpl;
    private final OtpRepository otpRepository;
    @Autowired
    public MyUserService(MyUserRepository myUserRepository,
                         EmailServiceImpl emailServiceImpl,
                         OtpRepository otpRepository) {
        this.myUserRepository = myUserRepository;
        this.emailServiceImpl = emailServiceImpl;
        this.otpRepository = otpRepository;
    }
    @Transactional
    public void sendOtpToSignupEmail(sendOtpToSignupEmailRequest sendOtpToSignupEmailRequest)
            throws SignupOtpAlreadyPresentException {
        String userEmail = sendOtpToSignupEmailRequest.getUserEmail();
        //Check if the email if not present in the Otp Table
        Otp checkOtp = otpRepository.findByUserEmailAndAccountSignup(
                userEmail,1
        );
        if(checkOtp!=null){
            throw new SignupOtpAlreadyPresentException("Otp for user email " + userEmail + " already present");
        }
        // Generate a random 6-digit OTP
        String otp = generateOtp();

        // Construct the email content
        String subject = "GuidebookX: OTP(One Time Password) for Signup Verification ";
        String text =
                "Welcome to GuidebookX! Your OTP for signup verification is: " + otp + ".\n\n" +
                "This OTP is valid for the next 5 minutes.\n\n" +
                "Thank you for choosing GuideBookX. We are dedicated to providing you with the best experience.\n\n" +
                "Best Regards,\n" +
                "GuideBookX Team";

        // Send the OTP to the user's email
        emailServiceImpl.sendSimpleMessage(userEmail, subject, text);

        // Save the OTP to the database
        Otp otpEntity = new Otp();
        otpEntity.setUserEmail(userEmail);
        otpEntity.setOtp(otp);
        otpEntity.setAccountSignup(1);
        otpRepository.save(otpEntity);
    }
    @Transactional
    public boolean verifySignupOtp(VerifySignupOtpRequest verifySignupOtpRequest) {
        // Use accountSignup = 1 for signup OTP verification
        Integer accountSignup = 1;

        // Find OTP based on userEmail and accountSignup
        Otp otpEntity = otpRepository.findByUserEmailAndAccountSignup(
                verifySignupOtpRequest.getUserEmail(),
                accountSignup);

        if (otpEntity != null) {
            String requestOtp = verifySignupOtpRequest.getSignupOtp();
            // Ensure correct type conversion if needed
            if (otpEntity.getOtp().equalsIgnoreCase(requestOtp)) {
                // If OTP matches, remove it from the table or mark it as used
                otpRepository.delete(otpEntity); // Optional, depends on your logic
                return true;
            }
        }
        return false;
    }
    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = random.nextInt(900000) + 100000; // Ensure a 6-digit number
        return String.valueOf(otp);
    }
}
