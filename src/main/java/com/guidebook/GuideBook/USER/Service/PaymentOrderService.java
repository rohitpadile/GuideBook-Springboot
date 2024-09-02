package com.guidebook.GuideBook.USER.Service;

import com.guidebook.GuideBook.ADMIN.Models.Student;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionForm;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.ADMIN.Services.ZoomSessionTransactionService;
import com.guidebook.GuideBook.ADMIN.Services.emailservice.EmailServiceImpl;
import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Models.PaymentOrder;
import com.guidebook.GuideBook.USER.Models.SubscriptionOrder;
import com.guidebook.GuideBook.USER.Repository.PaymentOrderRepository;
import com.guidebook.GuideBook.USER.Repository.SubscriptionOrderRepository;
import com.guidebook.GuideBook.USER.dtos.VerifyUserWithTransactionResponse;
import com.guidebook.GuideBook.USER.exceptions.MyUserAccountNotExistsException;
import com.guidebook.GuideBook.USER.exceptions.PaymentOrderSaveFailedException;
import com.guidebook.GuideBook.USER.exceptions.SubscriptionOrderSaveFailedException;
import com.razorpay.Order;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentOrderService {
    private final PaymentOrderRepository paymentOrderRepository;
    private final MyUserService myUserService;
    private final StudentMentorAccountService studentMentorAccountService;
    private final ClientAccountService clientAccountService;
    private final EmailServiceImpl emailServiceImpl;
    private final ZoomSessionTransactionService zoomSessionTransactionService;
    @Autowired
    public PaymentOrderService(PaymentOrderRepository paymentOrderRepository,
//                               SubscriptionOrderRepository subscriptionOrderRepository,
                               MyUserService myUserService,
                               StudentMentorAccountService studentMentorAccountService,
                               ClientAccountService clientAccountService,
                                ZoomSessionTransactionService zoomSessionTransactionService,
                               EmailServiceImpl emailServiceImpl) {
        this.paymentOrderRepository = paymentOrderRepository;
//        this.subscriptionOrderRepository = subscriptionOrderRepository;
        this.myUserService = myUserService;
        this.studentMentorAccountService = studentMentorAccountService;
        this.clientAccountService = clientAccountService;
        this.zoomSessionTransactionService = zoomSessionTransactionService;
        this.emailServiceImpl = emailServiceImpl;
    }

    @Transactional
    @NotNull
    public PaymentOrder addPaymentOrder(Order order, String userEmail)
            throws PaymentOrderSaveFailedException {

        try {
            PaymentOrder paymentOrder = new PaymentOrder();

            // Convert the Order object to a JSON object for easier manipulation
            JSONObject orderJson = new JSONObject(order.toString());

            // Safely retrieve values from the JSON object
            String orderId = orderJson.getString("id");
            int amount = orderJson.getInt("amount");
            String currency = orderJson.getString("currency");
            String receipt = orderJson.getString("receipt");
            String status = orderJson.getString("status");
            String createdAt = String.valueOf(orderJson.getLong("created_at"));

            // Get the nested notes JSON object
            JSONObject notes = orderJson.getJSONObject("notes");
            String customerUserEmail = notes.optString("customer_email", "");
            String gpayPhone = notes.optString("gpay_phone", "");
            String customerUsername = notes.optString("customer_username", "");
            String zoomSessionAmountPaid = notes.optString("Zoom Session Amount paid", "");

            // Set values in the new SubscriptionOrder entity
            paymentOrder.setPaymentRzpOrderId(orderId);
            paymentOrder.setPaymentUserEmail(customerUserEmail);
            paymentOrder.setPaymentUserGpayNumber(gpayPhone);
            paymentOrder.setPaymentUserName(customerUsername);
            paymentOrder.setPaymentAmount(String.valueOf(amount));
            paymentOrder.setPaymentCreatedAt(createdAt);
            paymentOrder.setPaymentCurrency(currency);
            paymentOrder.setPaymentReceipt(receipt);
            paymentOrder.setPaymentStatus(status);
            paymentOrder.setPaymentUserEmailAccountType(
                    myUserService.checkUserEmailAccountTypeGeneralPurpose(userEmail)
            );
            log.info("PaymentOrder created is: {}", paymentOrder);
            return paymentOrderRepository.save(paymentOrder);
        } catch (Exception e) {
            // Handle and log the exception
            throw new PaymentOrderSaveFailedException("Failed to save the payment order at addPaymentOrder() method. Rzp orderId = " + order.get("id"));
        }
    }
    @Transactional
    public VerifyUserWithTransactionResponse getZoomSessionPaymentPageDetails(String transactionId, String loggedInUserEmail) {
        ZoomSessionTransaction transaction = zoomSessionTransactionService.getZoomSessionTransactionById(transactionId);
        return VerifyUserWithTransactionResponse.builder()
                .zoomSessionBookStatus(transaction.getZoomSessionForm().getZoomSessionBookStatus())
                .zoomSessionDurationInMin(transaction.getZoomSessionForm().getZoomSessionDurationInMin().toString())
                .studentMentorName(transaction.getStudent().getStudentName())
                .build();
    }

    public PaymentOrder getPaymentOrderByRzpId(String rzpId){
        return paymentOrderRepository.findByPaymentRzpOrderId(rzpId);
    }
    public PaymentOrder updatePaymentOrder(PaymentOrder order){
        return paymentOrderRepository.save(order);
    }

    public void sendFinalConfirmationEmails(ZoomSessionTransaction transaction) {
        // Fetch details for emails
        ZoomSessionForm form = transaction.getZoomSessionForm();
        Student student = transaction.getStudent();
        String studentName = student.getStudentName();
        String clientName = form.getClientFirstName() + " " + form.getClientLastName();
        String clientEmail = form.getClientEmail();
        String studentEmail = student.getStudentWorkEmail();

        // Email content for client
        String clientSubject = "Zoom Session final Confirmation";
        String clientText = String.format(
                """
                        Dear %s,
    
                        Your session with %s is successfully booked.
                        Session duration: %s minutes.
                   
                        Please find the details and links scheduled by %s in the previous sent mail.
                        Have a great session. We look forward to hearing from you.
    
                        Best regards,
                        GuidebookX Team""",
                clientName, studentName, transaction.getZoomSessionForm().getZoomSessionDurationInMin(), studentName);

        // Email content for student
        String studentSubject = "Zoom Session final Confirmation";
        String studentText = String.format("""
                    Your Zoom session with %s has been successfully confirmed.
                    Please find the details and links in the previously sent mail.

                    Best regards,
                    GuidebookX Team""",
                clientName);

        // Send emails to client and student
        emailServiceImpl.sendSimpleMessage(clientEmail, clientSubject, clientText);
        emailServiceImpl.sendSimpleMessage(studentEmail, studentSubject, studentText);
    }



    @Transactional
    @NotNull
    public PaymentOrder addPaymentOrderGeneral(Order order, String userEmail)
            throws PaymentOrderSaveFailedException {

        try {
            PaymentOrder paymentOrder = new PaymentOrder();
            // Convert the Order object to a JSON object for easier manipulation
            JSONObject orderJson = new JSONObject(order.toString());

            // Safely retrieve values from the JSON object
            String orderId = orderJson.getString("id");
            int amount = orderJson.getInt("amount");
            String currency = orderJson.getString("currency");
            String receipt = orderJson.getString("receipt");
            String status = orderJson.getString("status");
            String createdAt = String.valueOf(orderJson.getLong("created_at"));

            // Get the nested notes JSON object
            JSONObject notes = orderJson.getJSONObject("notes");
            String customerUserEmail = notes.optString("customer_email", "");
            String gpayPhone = notes.optString("gpay_phone", "");
            String eventCode = notes.optString("EventCode", "");
//            String zoomSessionAmountPaid = notes.optString("Zoom Session Amount paid", "");
            String customerName= notes.optString("customer_username", "");
            String phoneNumber = notes.optString("phone_number", "");

            // Set values in the new SubscriptionOrder entity
            paymentOrder.setPaymentRzpOrderId(orderId);
            paymentOrder.setPaymentUserEmail(customerUserEmail);
//            paymentOrder.setPaymentUserGpayNumber(gpayPhone);
            paymentOrder.setEventCode(eventCode); //event code
            paymentOrder.setPaymentAmount(String.valueOf(amount));
            paymentOrder.setPaymentCreatedAt(createdAt);
            paymentOrder.setPaymentCurrency(currency);
            paymentOrder.setPaymentReceipt(receipt);
            paymentOrder.setPaymentStatus(status);
            paymentOrder.setPaymentUserClientPhoneNumber(phoneNumber); //client phone number
            paymentOrder.setPaymentUserName(customerName);
            paymentOrder.setPaymentUserEmailAccountType(
                    myUserService.checkUserEmailAccountTypeGeneralPurpose(userEmail)
            );
            log.info("PaymentOrder created is: {}", paymentOrder);
            return paymentOrderRepository.save(paymentOrder);
        } catch (Exception e) {
            // Handle and log the exception
            throw new PaymentOrderSaveFailedException("Failed to save the payment order at addPaymentOrder() method. Rzp orderId = " + order.get("id"));
        }
    }




}

