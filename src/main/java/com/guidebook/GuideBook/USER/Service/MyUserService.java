package com.guidebook.GuideBook.USER.Service;

import com.guidebook.GuideBook.ADMIN.Models.Language;
import com.guidebook.GuideBook.ADMIN.Models.Student;
import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.ADMIN.Services.emailservice.EmailServiceImpl;
import com.guidebook.GuideBook.ADMIN.exceptions.StudentBasicDetailsNotFoundException;
import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Models.Otp;
import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import com.guidebook.GuideBook.USER.Repository.MyUserRepository;
import com.guidebook.GuideBook.USER.Repository.OtpRepository;
import com.guidebook.GuideBook.USER.dtos.*;
import com.guidebook.GuideBook.USER.exceptions.ClientAccountNotFoundException;
import com.guidebook.GuideBook.USER.exceptions.SignupOtpAlreadyPresentException;
import com.guidebook.GuideBook.USER.exceptions.StudentMentorAccountNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserService {
    private final MyUserRepository myUserRepository;
    private final EmailServiceImpl emailServiceImpl;
    private final OtpRepository otpRepository;
    private final StudentMentorService studentMentorService;
    private final ClientAccountService clientAccountService;
    private final StudentService studentService;
    @Autowired
    public MyUserService(MyUserRepository myUserRepository,
                         EmailServiceImpl emailServiceImpl,
                         OtpRepository otpRepository,
                         StudentMentorService studentMentorService,
                         ClientAccountService clientAccountService,
                         StudentService studentService) {
        this.myUserRepository = myUserRepository;
        this.emailServiceImpl = emailServiceImpl;
        this.otpRepository = otpRepository;
        this.studentMentorService = studentMentorService;
        this.clientAccountService = clientAccountService;
        this.studentService = studentService;
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

    public CheckUserEmailAccountTypeResponse checkUserEmailAccountType(
            CheckUserEmailAccountTypeRequest request) {
        Integer accountType = null;
        if((studentMentorService.getAccountByEmail(request.getUserEmail())) != null){
            accountType = 1; //Student Mentor account
        } else if((clientAccountService.getAccountByEmail(request.getUserEmail())) != null){
            accountType = 2;//Client Account
        } else {
            accountType = 0;
        }

        return CheckUserEmailAccountTypeResponse.builder().accountType(accountType).build();

    }

    public ClientProfileAccountDetailsResponse getClientProfileAccountDetails(GetUserProfileAccountDetailsRequest request)
            throws ClientAccountNotFoundException {
        ClientAccount clientAccount = clientAccountService.getAccountByEmail(request.getUserEmail());
        if(clientAccount != null){
            return ClientProfileAccountDetailsResponse.builder()
                    .clientAccountEmail(request.getUserEmail())
                    .clientAccountZoomSessionCount(clientAccount.getClientAccountZoomSessionCount())
                    .clientAccountOfflineSessionCount(clientAccount.getClientAccountOfflineSessionCount())
                    .clientAccountSubscription_Monthly(clientAccount.getClientAccountSubscription_Monthly())
                    .build();

        } else {
            throw new ClientAccountNotFoundException("client account not found at getClientProfileAccountDetails() method");
        }
    }

    public StudentMentorProfileAccountDetailsResponse getStudentMentorProfileAccountDetails(GetUserProfileAccountDetailsRequest request)
            throws StudentMentorAccountNotFoundException, StudentBasicDetailsNotFoundException {
        StudentMentorAccount studentMentorAccount = studentMentorService.getAccountByEmail(request.getUserEmail());
        if(studentMentorAccount != null){
            Student student = studentService.getStudentByWorkEmail(request.getUserEmail());
            return StudentMentorProfileAccountDetailsResponse.builder()
                    .studentMentorAccountWorkEmail(studentMentorAccount.getStudentMentorAccountWorkEmail())
                    .studentMentorAccountSubscription_Monthly(studentMentorAccount.getStudentMentorAccountSubscription_Monthly())
                    .branch(student.getStudentBranch().getBranchName())
                    .examScore(student.getCetPercentile())
                    .grade(student.getGrade())
                    .yearOfStudy(student.getStudentClassType().getStudentClassTypeName())
                    .publicEmail(student.getStudentPublicEmail())
                    .college(student.getStudentCollege().getCollegeName())
                    .studentName(student.getStudentName())
                    .languagesSpoken(student.getStudentLanguageList().stream().map(Language::getLanguageName).toList())
                    .build();

        } else {
            throw new StudentMentorAccountNotFoundException("Student Mentor account not found at getStudentMentorProfileAccountDetails() method");
        }
    }
}
