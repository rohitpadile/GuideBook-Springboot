package com.guidebook.GuideBook.USER.Controller;

import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.USER.Service.*;
import com.guidebook.GuideBook.USER.dtos.*;
import com.guidebook.GuideBook.USER.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    private final TokenBlacklistService tokenBlacklistService;
    private final CustomUserDetailsService userDetailsService;
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
                            SubscriptionOrderService subscriptionOrderService,
                            TokenBlacklistService tokenBlacklistService,
                            CustomUserDetailsService userDetailsService
                            ) {
        this.myUserService = myUserService;
        this.jwtUtil = jwtUtil;
        this.studentMentorAccountService = studentMentorAccountService;
        this.clientAccountService = clientAccountService;
        this.studentService = studentService;
        this.subscriptionOrderService = subscriptionOrderService;
        this.tokenBlacklistService = tokenBlacklistService;
        this.userDetailsService = userDetailsService;
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

    @GetMapping("/checkLoginAndSubscription")
    public ResponseEntity<?> checkLogin(HttpServletRequest request)
            throws MyUserAccountNotExistsException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Check if the token is blacklisted
            if (tokenBlacklistService.isTokenBlacklisted(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Check token validity
            String username = jwtUtil.extractUsername(token);
            if (username != null && jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(username))) {
                //Also check subscription is active or not
                if(subscriptionOrderService.isMonthlySubscriptionActive(username)){
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/checkDummyAccount")
    public ResponseEntity<?> checkDummyAccount(HttpServletRequest request)
            throws MyUserAccountNotExistsException
    {
        String userEmail = jwtUtil.extractEmailFromToken(request);
        if(userEmail.equals("poemsby24@gmail.com")){
            log.info("Dummy account");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/isUserAStudentMentor")
    public ResponseEntity<Void> isUserAStudentMentor(@RequestBody Map<String, String> map,
                                                     HttpServletRequest request){
        String userEmail = jwtUtil.extractEmailFromToken(request);
        String studentWorkEmail = map.get("studentWorkEmail");
        log.info("User email : {}, studentWorkEmail: {}", userEmail, studentWorkEmail);
        if(userEmail.equals(studentWorkEmail)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}

