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

//    Creating order for payment
    @PostMapping("/createOrder")
    @Transactional
    public ResponseEntity<String> createOrder(
            @RequestBody @Valid CreateOrderSubscriptionRequest subscriptionRequest,
            HttpServletRequest request
    ) throws RazorpayException,
            SubscriptionNotFoundException,
            MyUserAccountNotExistsException,
            SubscriptionOrderSaveFailedException {

        String userEmail = jwtUtil.extractEmailFromToken(request);
        Long amt = myUserService.getSubscriptionAmountForPlan(subscriptionRequest.getSubscriptionPlan());

        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
        JSONObject orderRequest = getOrderRequest(amt, userEmail, subscriptionRequest.getSubscriptionPlan());
        Order order = razorpay.orders.create(orderRequest);
        log.info("Order created is : {}", order);

        //Save the order to the database
        //rzp order id is important
        subscriptionOrderService.addSubscriptionOrder(order, userEmail);
        return new ResponseEntity<>(order.toString(), HttpStatus.OK);
    }

    @NotNull
    @Transactional
    private JSONObject getOrderRequest(Long amount, String userEmail, String subPlan)
            throws SubscriptionNotFoundException,
            MyUserAccountNotExistsException {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100); // Converted to paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", generateReceiptId(userEmail));
        // Using the generated receipt ID, max 40 char

        JSONObject notes = new JSONObject();
        if(myUserService.checkUserEmailAccountTypeGeneralPurpose(userEmail) == 1){
            notes.put("customer_username",
                    studentService.getStudentByWorkEmail(userEmail).getStudentName());
        } else if(myUserService.checkUserEmailAccountTypeGeneralPurpose(userEmail) == 2){
            ClientAccount account = clientAccountService.getAccountByEmail(userEmail);
            notes.put("customer_username",
                    account.getClientFirstName() + " " +
                    account.getClientMiddleName()+ " " +
                    account.getClientLastName());
        }else {
            throw new MyUserAccountNotExistsException("MyUser has no account at getOrderRequest() method");
        }
        notes.put("customer_email", userEmail); // Storing user email in notes
        notes.put("gpay_phone", ""); // Placeholder for GPay/Phone number. Add the actual number if available.
        notes.put("subscription_type", subPlan); // You can add more user-specific info, like the subscription type
        notes.put("subscription_amount", myUserService.getSubscriptionAmountForPlan(subPlan));

        orderRequest.put("notes", notes);
        return orderRequest;
    }
    private String generateReceiptId(String userEmail) {
        //Max receipt size is 40 characters
        if(userEmail.length() > 40){
            return userEmail.substring(0,40);
        }
        return userEmail;

    }
//    CREATE METHOD FOR STATUS PAID, PAYMENT ID STORING, AND ACTIVATING GIVEN SUBSCRIPTION PLAN OF USER
    @PostMapping("/activateSubscription")
    @Transactional
    public ResponseEntity<Void> activateSubscription(
            @RequestBody @Valid SubscriptionOrderPaymentSuccessRequest successRequest,
            HttpServletRequest request
    ) throws SubscriptionOrderNotFoundException,
            SubscriptionActivationFailedException {
        String userEmail = jwtUtil.extractEmailFromToken(request);
        subscriptionOrderService.activateSubscriptionForUseremail(successRequest, userEmail);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

//See video - 2
    @PostMapping("/getSubscriptionAmount")
    @Transactional
    public ResponseEntity<SubscriptionAmountResponse> getSubscriptionAmount(
            @RequestBody @Valid GetSubscriptionAmountRequest request)
            throws SubscriptionNotFoundException {
        SubscriptionAmountResponse res = myUserService.getSubscriptionAmount(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
//GENERAL FORMAT OF ORDER RECEIVED IN MY PROJECT FROM RZP
//{
//        "amount":2000,
//        "amount_paid":0,
//        "notes":
//        {
//        "subscription_amount":20,
//        "customer_email":"rohitp22.elec@coeptech.ac.in",
//        "gpay_phone":"",
//        "customer_username":"Rohit Padile",
//        "subscription_type":"monthly"
//        },
//        "created_at":1723634031,
//        "amount_due":2000,
//        "currency":"INR",
//        "receipt":"rohitp22.elec@coeptech.ac.in",
//        "id":"order_Okl6vpXCUal0EG",
//        "entity":"order",
//        "offer_id":null,
//        "attempts":0,
//        "status":"created"
//        }
