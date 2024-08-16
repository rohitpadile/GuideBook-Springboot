package com.guidebook.GuideBook.USER.Service;

import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.ADMIN.Services.ZoomSessionTransactionService;
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
    private final ZoomSessionTransactionService zoomSessionTransactionService;
    @Autowired
    public PaymentOrderService(PaymentOrderRepository paymentOrderRepository,
//                               SubscriptionOrderRepository subscriptionOrderRepository,
                               MyUserService myUserService,
                               StudentMentorAccountService studentMentorAccountService,
                               ClientAccountService clientAccountService,
                                ZoomSessionTransactionService zoomSessionTransactionService) {
        this.paymentOrderRepository = paymentOrderRepository;
//        this.subscriptionOrderRepository = subscriptionOrderRepository;
        this.myUserService = myUserService;
        this.studentMentorAccountService = studentMentorAccountService;
        this.clientAccountService = clientAccountService;
        this.zoomSessionTransactionService = zoomSessionTransactionService;
    }

    @Transactional
    @NotNull
    public void addPaymentOrder(Order order, String userEmail)
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
            paymentOrderRepository.save(paymentOrder);

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
}

