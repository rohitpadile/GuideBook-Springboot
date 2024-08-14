package com.guidebook.GuideBook.USER.Controller;

import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Service.*;
import com.guidebook.GuideBook.USER.dtos.GetSubscriptionAmountRequest;
import com.guidebook.GuideBook.USER.dtos.*;
import com.guidebook.GuideBook.USER.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.razorpay.*;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/user/")
@Slf4j
public class MyUserController {
    private final MyUserService myUserService;
    private final JwtUtil jwtUtil;
    private final StudentMentorAccountService studentMentorAccountService;
    private final ClientAccountService clientAccountService;
    private final StudentService studentService;
    private final SubscriptionOrderService subscriptionOrderService;
    @Value("${razorpay_key_id}")
    private String razorpayKeyId;
    @Value("${razorpay_key_secret}")
    private String razorpayKeySecret;
    @Autowired
    public MyUserController(MyUserService myUserService,
                            JwtUtil jwtUtil,
                            ClientAccountService clientAccountService,
                            StudentMentorAccountService studentMentorAccountService,
                            StudentService studentService,
                            SubscriptionOrderService subscriptionOrderService
                            ) {
        this.myUserService = myUserService;
        this.jwtUtil = jwtUtil;
        this.studentMentorAccountService = studentMentorAccountService;
        this.clientAccountService = clientAccountService;
        this.studentService = studentService;
        this.subscriptionOrderService = subscriptionOrderService;
    }

    @PostMapping("/sendOtpToSignupEmail")
    public ResponseEntity<Void> sendOtpToSignupEmail(
            @RequestBody @Valid sendOtpToSignupEmailRequest sendOtpToSignupEmailRequest)
            throws SignupOtpAlreadyPresentException {
        myUserService.sendOtpToSignupEmail(sendOtpToSignupEmailRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/verifySignupOtp")
    public ResponseEntity<Void> verifySignupOtp(@RequestBody @Valid VerifySignupOtpRequest verifySignupOtpRequest) {
        boolean isValidOtp = myUserService.verifySignupOtp(verifySignupOtpRequest);
        if (isValidOtp) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/checkUserEmailAccountType")
//    public ResponseEntity<CheckUserEmailAccountTypeResponse>  checkUserEmailAccountType(
//            @RequestBody @Valid CheckUserEmailAccountTypeRequest request
//            ){
//        CheckUserEmailAccountTypeResponse res = myUserService.checkUserEmailAccountType(request);
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }
//
//    @PostMapping("/getClientProfileAccountDetails")
//    public ResponseEntity<ClientProfileAccountDetailsResponse> getClientProfileAccountDetails(
//            @RequestBody @Valid GetUserProfileAccountDetailsRequest request
//    ) throws ClientAccountNotFoundException {
//        ClientProfileAccountDetailsResponse res = myUserService.getClientProfileAccountDetails(request);
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }
//
//    @PostMapping("/getStudentMentorProfileAccountDetails")
//    public ResponseEntity<StudentMentorProfileAccountDetailsResponse> getStudentMentorProfileAccountDetails(
//            @RequestBody @Valid GetUserProfileAccountDetailsRequest request
//    ) throws StudentMentorAccountNotFoundException,
//            StudentBasicDetailsNotFoundException {
//        StudentMentorProfileAccountDetailsResponse res = myUserService.getStudentMentorProfileAccountDetails(request);
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }

    @PostMapping("/checkUserEmailAccountType")
    public ResponseEntity<CheckUserEmailAccountTypeResponse> checkUserEmailAccountType(HttpServletRequest request) {
        String userEmail = jwtUtil.extractEmailFromToken(request);
        CheckUserEmailAccountTypeRequest req = new CheckUserEmailAccountTypeRequest();
        req.setUserEmail(userEmail);
        CheckUserEmailAccountTypeResponse res = myUserService.checkUserEmailAccountTypeGeneralPurpose(req);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/getClientProfileAccountDetails")
    public ResponseEntity<ClientProfileAccountDetailsResponse> getClientProfileAccountDetails(HttpServletRequest request)
            throws ClientAccountNotFoundException {
        String userEmail = jwtUtil.extractEmailFromToken(request);
        GetUserProfileAccountDetailsRequest req = new GetUserProfileAccountDetailsRequest();
        req.setUserEmail(userEmail);
        ClientProfileAccountDetailsResponse res = myUserService.getClientProfileAccountDetails(req);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/getStudentMentorProfileAccountDetails")
    public ResponseEntity<StudentMentorProfileAccountDetailsResponse> getStudentMentorProfileAccountDetails(HttpServletRequest request)
            throws Exception {
        String userEmail = jwtUtil.extractEmailFromToken(request);
        GetUserProfileAccountDetailsRequest req = new GetUserProfileAccountDetailsRequest();
        req.setUserEmail(userEmail);
        StudentMentorProfileAccountDetailsResponse res = myUserService.getStudentMentorProfileAccountDetails(req);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}

