package com.guidebook.GuideBook.USER.Service;

import com.guidebook.GuideBook.ADMIN.Models.Language;
import com.guidebook.GuideBook.ADMIN.Models.Student;
import com.guidebook.GuideBook.ADMIN.Models.StudentProfile;
import com.guidebook.GuideBook.ADMIN.Services.StudentProfileService;
import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.ADMIN.Services.emailservice.EmailServiceImpl;
import com.guidebook.GuideBook.USER.Models.MyUser;
import com.guidebook.GuideBook.USER.dtos.GetSubscriptionAmountRequest;
import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Models.Otp;
import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import com.guidebook.GuideBook.USER.Repository.MyUserRepository;
import com.guidebook.GuideBook.USER.Repository.OtpRepository;
import com.guidebook.GuideBook.USER.dtos.*;
import com.guidebook.GuideBook.USER.exceptions.ClientAccountNotFoundException;
import com.guidebook.GuideBook.USER.exceptions.SignupOtpAlreadyPresentException;
import com.guidebook.GuideBook.USER.exceptions.StudentMentorAccountNotFoundException;
import com.guidebook.GuideBook.USER.exceptions.SubscriptionNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Data
public class MyUserService {
    @Value("${submonthly}")
    private Long subMonthly;

    private Long subYearly;
    private final MyUserRepository myUserRepository;
    private final EmailServiceImpl emailServiceImpl;
    private final OtpRepository otpRepository;
    private final StudentMentorAccountService studentMentorAccountService;
    private final ClientAccountService clientAccountService;
    private final StudentService studentService;
    private final StudentProfileService studentProfileService;
    @Autowired
    public MyUserService(MyUserRepository myUserRepository,
                         EmailServiceImpl emailServiceImpl,
                         OtpRepository otpRepository,
                         StudentMentorAccountService studentMentorAccountService,
                         ClientAccountService clientAccountService,
                         StudentService studentService,
                         StudentProfileService studentProfileService) {
        this.myUserRepository = myUserRepository;
        this.emailServiceImpl = emailServiceImpl;
        this.otpRepository = otpRepository;
        this.studentMentorAccountService = studentMentorAccountService;
        this.clientAccountService = clientAccountService;
        this.studentService = studentService;
        this.studentProfileService = studentProfileService;
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

    public CheckUserEmailAccountTypeResponse checkUserEmailAccountTypeGeneralPurpose(
            CheckUserEmailAccountTypeRequest request) {
        Integer accountType = null;
        //Always I must use if and else if to retrieve the account. Also it will be only one of them. DB is clean in that sense.
        if((studentMentorAccountService.getAccountByEmail(request.getUserEmail())) != null){
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

                    .clientFirstName(clientAccount.getClientFirstName())
                    .clientMiddleName(clientAccount.getClientMiddleName())
                    .clientLastName(clientAccount.getClientLastName())
                    .clientCollege(clientAccount.getClientCollege())
                    .clientAge(clientAccount.getClientAge())
                    .clientPhoneNumber(clientAccount.getClientPhoneNumber())
                    .clientValidProof(clientAccount.getClientValidProof())
                    .clientZoomEmail(clientAccount.getClientZoomEmail())
                    .build();

        } else {
            throw new ClientAccountNotFoundException("client account not found at getClientProfileAccountDetails() method");
        }
    }

    public StudentMentorProfileAccountDetailsResponse getStudentMentorProfileAccountDetails(GetUserProfileAccountDetailsRequest request)
            throws Exception {
        StudentMentorAccount studentMentorAccount = studentMentorAccountService.getAccountByEmail(request.getUserEmail());
        if(studentMentorAccount != null){
            Student student = studentService.getStudentByWorkEmail(request.getUserEmail());
            StudentProfile profile = studentProfileService.getStudentProfileForGeneralPurpose(request.getUserEmail());
            log.info("Link for student public profile edit: {}",studentService.getEditStudentProfileLink(student));
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
                    .studentProfileSessionsConducted(profile.getStudentProfileSessionsConducted())
                    .editStudentProfileLink(studentService.getEditStudentProfileLink(student))
                    .studentMentorAccountZoomSessionCount(studentMentorAccount.getStudentMentorAccountZoomSessionCount())
                    .studentMentorAccountOfflineSessionCount(studentMentorAccount.getStudentMentorAccountOfflineSessionCount())

                    .zoomSessionsRemainingPerWeek(profile.getZoomSessionsRemainingPerWeek())
                    .zoomSessionsPerWeek(profile.getZoomSessionsPerWeek())

                    .clientFirstName(studentMentorAccount.getClientFirstName())
                    .clientMiddleName(studentMentorAccount.getClientMiddleName())
                    .clientLastName(studentMentorAccount.getClientLastName())
                    .clientAge(studentMentorAccount.getClientAge())
                    .clientPhoneNumber(studentMentorAccount.getClientPhoneNumber())
                    .clientValidProof(studentMentorAccount.getClientValidProof())
                    .clientZoomEmail(studentMentorAccount.getClientZoomEmail())
                    .build();


        } else {
            throw new StudentMentorAccountNotFoundException("Student Mentor account not found at getStudentMentorProfileAccountDetails() method");
        }
    }

    public SubscriptionAmountResponse getSubscriptionAmount(GetSubscriptionAmountRequest request)
            throws SubscriptionNotFoundException {
        if(request.getSubscriptionPlan()!=null && request.getSubscriptionPlan().equalsIgnoreCase( "monthly")){
            return SubscriptionAmountResponse.builder()
                    .subAmount(subMonthly.toString())
                    .build();
            //Make else if and add yearly subscription when available
        } else{
            throw new SubscriptionNotFoundException("Subscription type not found at getSubscriptionAmount() method, or plan provided is null");
        }
    }
    public Long getSubscriptionAmountForPlan(String subscriptionPlan)
            throws SubscriptionNotFoundException {
        if(subscriptionPlan.equalsIgnoreCase( "monthly")){
            return subMonthly;
            //Make else if and add yearly subscription when available
        } else{
            throw new SubscriptionNotFoundException("Subscription type not found at getSubscriptionAmountForGeneral() method");
        }
    }

    public Integer checkUserEmailAccountTypeGeneralPurpose(String userEmail) {
        //Always I must use if and else if to retrieve the account. Also it will be only one of them. DB is clean in that sense.
        if((studentMentorAccountService.getAccountByEmail(userEmail)) != null){
            //Student Mentor account exists
            return 1;
        } else if((clientAccountService.getAccountByEmail(userEmail)) != null){
            //Client Account exists
            return 2;
        }
        //If client Account and mentor account both are null - then there is a big problem, 0 is returned
        //But this will be a rare case.
        return 0;

    }

    public IsUserAStudentMentorResponse isUserAStudentMentor(Map<String, String> map, String userEmail) {

        if(userEmail.equals(map.get("studentWorkEmail"))){
            log.info("User email : {}, studentWorkEmail: {}", userEmail, map.get("studentWorkEmail"));
            return IsUserAStudentMentorResponse.builder().isUserAStudentMentor(1).build();
        } else {
            return IsUserAStudentMentorResponse.builder().isUserAStudentMentor(0).build();
        }
    }

    public List<MyUser> getAllMyUsers() {
        return myUserRepository.findAll();
    }

    public MyUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return myUserRepository.findByUsername(currentUserName);
    }
}
