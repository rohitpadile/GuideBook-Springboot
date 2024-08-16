package com.guidebook.GuideBook.USER.Controller;

import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.ADMIN.Services.ZoomSessionTransactionService;
import com.guidebook.GuideBook.ADMIN.Services.zoomsessionbook.ZoomSessionFormService;
import com.guidebook.GuideBook.ADMIN.exceptions.TransactionNotFoundException;
import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Models.PaymentOrder;
import com.guidebook.GuideBook.USER.Service.*;
import com.guidebook.GuideBook.USER.dtos.CreateOrderPaymentZoomSessionRequest;
import com.guidebook.GuideBook.USER.dtos.CreateOrderSubscriptionRequest;
import com.guidebook.GuideBook.USER.exceptions.*;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/user/")
@Slf4j
@Data
public class PaymentOrderController {
    private final PaymentOrderService paymentOrderService;
    private final MyUserService myUserService;
    private final JwtUtil jwtUtil;
    private final StudentMentorAccountService studentMentorAccountService;
    private final ClientAccountService clientAccountService;
    private final StudentService studentService;
    private final SubscriptionOrderService subscriptionOrderService;
    private final ZoomSessionTransactionService zoomSessionTransactionService;
    private final ZoomSessionFormService zoomSessionFormService;
    @Value("${razorpay_key_id}")
    private String razorpayKeyId;
    @Value("${razorpay_key_secret}")
    private String razorpayKeySecret;
    @Value("${individualzoomsessionamount30min}")
    private String individualZoomSessionAmount30Min;
    @Value("${individualzoomsessionamount15min}")
    private String individualZoomSessionAmount15Min;
    @Autowired
    public PaymentOrderController(PaymentOrderService paymentOrderService,
                                  MyUserService myUserService,
                                  JwtUtil jwtUtil,
                                  StudentMentorAccountService studentMentorAccountService,
                                  ClientAccountService clientAccountService,
                                  StudentService studentService,
                                  SubscriptionOrderService subscriptionOrderService,
                                  ZoomSessionTransactionService zoomSessionTransactionService,
                                  ZoomSessionFormService zoomSessionFormService) {
        this.paymentOrderService = paymentOrderService;
        this.myUserService = myUserService;
        this.jwtUtil = jwtUtil;
        this.studentMentorAccountService = studentMentorAccountService;
        this.clientAccountService = clientAccountService;
        this.zoomSessionFormService = zoomSessionFormService;
        this.studentService = studentService;
        this.subscriptionOrderService = subscriptionOrderService;
        this.zoomSessionTransactionService = zoomSessionTransactionService;
    }

    @PostMapping("/createPaymentOrderZoomSession")
    @Transactional
    public ResponseEntity<String> createPaymentOrderZoomSession(
            @RequestBody @Valid CreateOrderPaymentZoomSessionRequest orderPaymentZoomSessionRequest,
            HttpServletRequest request
    ) throws RazorpayException,
            MyUserAccountNotExistsException,
            SubscriptionNotFoundException,
            TransactionNotFoundException,
            PaymentOrderSaveFailedException {

        String userEmail = jwtUtil.extractEmailFromToken(request);

        RazorpayClient razorpay = new RazorpayClient(razorpayKeySecret);
        JSONObject orderRequest = getOrderRequestZoomSession(userEmail, orderPaymentZoomSessionRequest);
        Order order = razorpay.orders.create(orderRequest);
        log.info("Order created is : {}", order);

//        paymentOrderService.addPaymentOrder(order,userEmail);
//        private String paymentAmount; //impt
//        private String paymentUserEmail;
//        private Integer paymentUserEmailAccountType;//1 for mentor, 2 for client
//        private String paymentUserGpayNumber;
//        private String paymentUserName;
//        private String paymentCreatedAt;
//        private String paymentCurrency;
//        private String paymentReceipt; //impt
//        private String paymentRzpOrderId; //impt
//        private String paymentStatus;
//
//        private String paymentId; //impt
        return new ResponseEntity<>(order.toString(), HttpStatus.OK);
    }

    @NotNull
    @Transactional
    private JSONObject getOrderRequestZoomSession(String userEmail, CreateOrderPaymentZoomSessionRequest request)
            throws SubscriptionNotFoundException,
            MyUserAccountNotExistsException,
            TransactionNotFoundException {
        JSONObject orderRequest = new JSONObject();
        Long amt = null;
        ZoomSessionTransaction transaction = zoomSessionTransactionService.getZoomSessionTransactionById(request.getZoomSessionTransactionId());
        if(transaction != null){
            if(transaction.getZoomSessionForm().getZoomSessionDurationInMin().toString()
                    .equalsIgnoreCase("15")){
                amt = Long.parseLong(this.individualZoomSessionAmount15Min);
            } else if(transaction.getZoomSessionForm().getZoomSessionDurationInMin().toString()
                    .equalsIgnoreCase("30")){
                amt = Long.parseLong(this.individualZoomSessionAmount30Min);
            } else { //someone has changed the duration by inspecting webpage
                amt = Long.parseLong(this.getIndividualZoomSessionAmount30Min());
                //If someone changed the default amount number, we wil charge him the max amount so that he does not do it again
            }
        } else {
            throw new TransactionNotFoundException("Zoom Session transaction not found at getOrderRequestZoomSession() method");
        }
        orderRequest.put("amount", amt * 100); // Converted to paise
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
            throw new MyUserAccountNotExistsException("MyUser has no account at getOrderRequestZoomSession() method");
        }
        notes.put("customer_email", userEmail); // Storing user email in notes
        notes.put("gpay_phone", ""); // Placeholder for GPay/Phone number. Add the actual number if available.
        notes.put("Zoom Session Duration(min)", transaction.getZoomSessionForm().getZoomSessionDurationInMin()); // You can add more user-specific info, like the subscription type
        notes.put("Zoom Session Amount paid", amt);

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


}
