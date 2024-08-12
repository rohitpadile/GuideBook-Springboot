package com.guidebook.GuideBook.USER.Controller;

import com.guidebook.GuideBook.ADMIN.exceptions.StudentBasicDetailsNotFoundException;
import com.guidebook.GuideBook.ADMIN.exceptions.StudentProfileContentNotFoundException;
import com.guidebook.GuideBook.USER.Service.JwtUtil;
import com.guidebook.GuideBook.USER.Service.MyUserService;
import com.guidebook.GuideBook.USER.dtos.*;
import com.guidebook.GuideBook.USER.exceptions.ClientAccountNotFoundException;
import com.guidebook.GuideBook.USER.exceptions.SignupOtpAlreadyPresentException;
import com.guidebook.GuideBook.USER.exceptions.StudentMentorAccountNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.razorpay.*;
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
    @Value("${razorpay_key_id}")
    private String razorpayKeyId;
    @Value("${razorpay_key_secret}")
    private String razorpayKeySecret;
    @Autowired
    public MyUserController(MyUserService myUserService,
                            JwtUtil jwtUtil) {
        this.myUserService = myUserService;
        this.jwtUtil = jwtUtil;
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
        CheckUserEmailAccountTypeResponse res = myUserService.checkUserEmailAccountType(req);
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

    //Creating order for payment
//    @PostMapping("/createOrder")
//    public ResponseEntity<String> createOrder(
//            @RequestBody @Valid Map<String, Object> data
//    ) throws RazorpayException {
//        Integer amt = Integer.parseInt(data.get("amount").toString());
//
//        //Using razor pay api to generate the order
//        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
//        JSONObject orderRequest = getOrderRequest();
//        Order order = razorpay.orders.create(orderRequest);
//        log.info("Order created is : {}", order);
//
//        //Save the order to the database
//        //order id is important he repeats
//
//        return new ResponseEntity<>(order.toString(), HttpStatus.OK);
//    }
//
//    @NotNull
//    private static JSONObject getOrderRequest() {
//        JSONObject orderRequest = new JSONObject();
//        orderRequest.put("amount",100); //Rs. 1
//        orderRequest.put("currency","INR");
//        orderRequest.put("receipt", "receipt#1"); //Use userEmail in the receipt and use the time stamp with it. Create a private function for that
//        JSONObject notes = new JSONObject();
//        notes.put("notes_key_1","Tea, Earl Grey, Hot"); //Put the info from the user account in this notes. Also add Gpay Phone Number to the account so that refund will be easy.
//        orderRequest.put("notes",notes);
//        return orderRequest;
//    }


//    CREATE METHOD FOR STATUS PAID, ORDER ID STORING, PAYMENT ID STORING, AND ACTIVATING MONTHLY SUBSCRIPTION OF USER

}
