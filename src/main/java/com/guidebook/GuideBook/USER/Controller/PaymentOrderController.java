package com.guidebook.GuideBook.USER.Controller;

import com.guidebook.GuideBook.ADMIN.Models.Student;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionForm;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.ADMIN.Services.ZoomSessionTransactionService;
import com.guidebook.GuideBook.ADMIN.Services.emailservice.EmailServiceImpl;
import com.guidebook.GuideBook.ADMIN.Services.zoomsessionbook.ZoomSessionFormService;
import com.guidebook.GuideBook.ADMIN.enums.ZoomSessionBookStatus;
import com.guidebook.GuideBook.ADMIN.exceptions.TransactionNotFoundException;
import com.guidebook.GuideBook.ADMIN.exceptions.ZoomSessionNotFoundException;
import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Models.PaymentOrder;
import com.guidebook.GuideBook.USER.Service.*;
import com.guidebook.GuideBook.USER.dtos.CreateOrderPaymentZoomSessionRequest;
import com.guidebook.GuideBook.USER.dtos.PaymentSucessForZoomSessionRequest;
import com.guidebook.GuideBook.USER.dtos.VerifyUserWithTransactionResponse;
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

import java.util.Optional;

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
    private final EmailServiceImpl emailServiceImpl;
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
                                  EmailServiceImpl emailServiceImpl,
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
        this.emailServiceImpl = emailServiceImpl;
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
        log.info("Recieved order DTO : {}", orderPaymentZoomSessionRequest);
        String userEmail = jwtUtil.extractEmailFromToken(request);
        log.info("User email : {}", userEmail);

        RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
        JSONObject orderRequest = getOrderRequestZoomSession(userEmail, orderPaymentZoomSessionRequest);
        Order order = razorpay.orders.create(orderRequest);
        log.info("Order created is : {}", order);

        PaymentOrder savedOrder = paymentOrderService.addPaymentOrder(order, userEmail);
        //Add this saved Order to the transaction entity also
        zoomSessionTransactionService.getZoomSessionTransactionById(orderPaymentZoomSessionRequest.getZoomSessionTransactionId())
                .setPaymentOrderRzpId(savedOrder.getPaymentRzpOrderId());

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
        if (transaction != null) {
            if (transaction.getZoomSessionForm().getZoomSessionDurationInMin().toString()
                    .equalsIgnoreCase("15")) {
                amt = Long.parseLong(this.individualZoomSessionAmount15Min);
            } else if (transaction.getZoomSessionForm().getZoomSessionDurationInMin().toString()
                    .equalsIgnoreCase("30")) {
                amt = Long.parseLong(this.individualZoomSessionAmount30Min);
            } else { //someone has changed the duration by inspecting webpage
                amt = Long.parseLong(this.individualZoomSessionAmount30Min);
                //If someone changed the default amount number, we wil charge him the max amount so that he does not do it again
            }
        } else {
            throw new TransactionNotFoundException("Zoom Session transaction not found at getOrderRequestZoomSession() method");
        }
        log.info("I am going to put key-value pairs in the json object now");
        orderRequest.put("amount", amt * 100); // Converted to paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", generateReceiptId(userEmail));
        // Using the generated receipt ID, max 40 char

        JSONObject notes = new JSONObject();
        if (myUserService.checkUserEmailAccountTypeGeneralPurpose(userEmail) == 1) {
            notes.put("customer_username",
                    studentService.getStudentByWorkEmail(userEmail).getStudentName());
        } else if (myUserService.checkUserEmailAccountTypeGeneralPurpose(userEmail) == 2) {
            ClientAccount account = clientAccountService.getAccountByEmail(userEmail);
            notes.put("customer_username",
                    account.getClientFirstName() + " " +
                            account.getClientMiddleName() + " " +
                            account.getClientLastName());
        } else {
            throw new MyUserAccountNotExistsException("MyUser has no account at getOrderRequestZoomSession() method");
        }
        notes.put("customer_email", userEmail); // Storing user email in notes
        notes.put("gpay_phone", ""); // Placeholder for GPay/Phone number. Add the actual number if available.
        notes.put("Zoom Session Duration(min)", transaction.getZoomSessionForm().getZoomSessionDurationInMin()); // You can add more user-specific info, like the subscription type
        notes.put("Zoom Session Amount paid", amt);

        orderRequest.put("notes", notes);
        log.info("key-value pairs in the json object done: {}", orderRequest);
        return orderRequest;
    }

    private String generateReceiptId(String userEmail) {
        //Max receipt size is 40 characters
        if (userEmail.length() > 40) {
            return userEmail.substring(0, 40);
        }
        return userEmail;

    }

    @GetMapping("/verifyUserWithTransaction/{transactionId}")
    @Transactional
    public ResponseEntity<VerifyUserWithTransactionResponse> verifyUserWithTransactionId(@PathVariable String transactionId,
                                                                                         HttpServletRequest request) {
        log.info("Transaction id: {}", transactionId);
        String loggedInUserEmail = jwtUtil.extractEmailFromToken(request);
        String transactionUserEmail = zoomSessionTransactionService.getZoomSessionTransactionById(transactionId)
                .getZoomSessionForm().getUserEmail();
        log.info("Transaction User email: {}, login user email: {}", transactionUserEmail, loggedInUserEmail);
        if (transactionUserEmail.equals(loggedInUserEmail)) {
            VerifyUserWithTransactionResponse res = paymentOrderService.getZoomSessionPaymentPageDetails(transactionId, loggedInUserEmail);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/verifyUserWithZoomSessionFormId/{zoomSessionFormId}")
    @Transactional
    public ResponseEntity<Void> verifyUserWithZoomSessionFormId(
            @PathVariable String zoomSessionFormId,
            HttpServletRequest request)
            throws ZoomSessionNotFoundException {
//        log.info("zoomSessionFormId for cancellation is : {}" ,zoomSessionFormId);
        String loggedInUserEmail = jwtUtil.extractEmailFromToken(request);
        Optional<ZoomSessionForm> checkForm = zoomSessionFormService.getZoomSessionFormById(zoomSessionFormId);
        if (checkForm.isPresent()) {
            ZoomSessionForm form = checkForm.get();
            if (loggedInUserEmail.equals(form.getUserEmail())) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new ZoomSessionNotFoundException("Zoom Session form not found at verifyUserWithZoomSessionFormId() method");
        }
    }

    @PostMapping("/paymentSuccessForZoomSession")
    @Transactional
    public ResponseEntity<Void> paymentSuccessForZoomSession(
            @RequestBody @Valid PaymentSucessForZoomSessionRequest paymentSucessForZoomSessionRequest,
            HttpServletRequest request
    ) throws TransactionNotFoundException {

        String loggedInUserEmail = jwtUtil.extractEmailFromToken(request);
        ZoomSessionTransaction transaction = zoomSessionTransactionService.getZoomSessionTransactionById(paymentSucessForZoomSessionRequest.getZoomSessionTransactionId());

        if (transaction != null) {
            String transactionUserEmail = transaction.getZoomSessionForm().getUserEmail();
            if (transactionUserEmail.equals(loggedInUserEmail)) {
                // Update transaction and payment order status
                transaction.setTransactionStatus("paid");
                transaction.setTransactionAmount(
                        Double.valueOf(transaction.getZoomSessionForm().getZoomSessionDurationInMin().toString()
                                .equalsIgnoreCase("15") ? this.individualZoomSessionAmount15Min : this.individualZoomSessionAmount30Min)
                );
                PaymentOrder order = paymentOrderService.getPaymentOrderByRzpId(transaction.getPaymentOrderRzpId());
                order.setPaymentStatus("paid");
                order.setPaymentId(paymentSucessForZoomSessionRequest.getPaymentId());

                // Update Zoom session form status
                transaction.getZoomSessionForm().setZoomSessionBookStatus(ZoomSessionBookStatus.BOOKED.toString());
//SEND EMAIL TO BOTH FOR LAST CONFIRMATION

                // Fetch details for emails
                ZoomSessionForm form = transaction.getZoomSessionForm();
                Student student = transaction.getStudent();
                String studentName = student.getStudentName();
                String clientName = form.getClientFirstName() + " " + form.getClientLastName();
                String clientEmail = form.getClientEmail();
                String studentEmail = student.getStudentWorkEmail();

                // Email content
                String clientSubject = "Zoom Session final Confirmation";
                String clientText = String.format(
                        """
                                Dear %s,

                                Your payment has been successfully processed.
                                You can proceed with the session now.
                           
                                Please find the details and links scheduled by %s in the previous sent mail.
                                Have a great session. We look forward to hear from you.
                                
                                Best regards,
                                GuidebookX Team""",
                        clientName, studentName);

                String studentSubject = "Zoom Session final Confirmation";
                String studentText = String.format("""
                                Your Zoom session with %s has been successfully confirmed.
                                Please find the details and links in the previous sent mail

                                Best regards,
                                GuidebookX Team""",
                        clientName);

                // Send emails
                emailServiceImpl.sendSimpleMessage(clientEmail, clientSubject, clientText);
                emailServiceImpl.sendSimpleMessage(studentEmail, studentSubject, studentText);

                // Save updated entities
                zoomSessionTransactionService.saveZoomSessionTransaction(transaction);
                paymentOrderService.updatePaymentOrder(order);
                zoomSessionFormService.updateZoomSessionForm(form);

                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new TransactionNotFoundException("Zoom Session Transaction not found at paymentSuccessForZoomSession() method");
        }
    }

}
