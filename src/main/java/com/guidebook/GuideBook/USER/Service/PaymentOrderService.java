package com.guidebook.GuideBook.USER.Service;

import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Models.PaymentOrder;
import com.guidebook.GuideBook.USER.Models.SubscriptionOrder;
import com.guidebook.GuideBook.USER.Repository.PaymentOrderRepository;
import com.guidebook.GuideBook.USER.Repository.SubscriptionOrderRepository;
import com.guidebook.GuideBook.USER.exceptions.MyUserAccountNotExistsException;
import com.guidebook.GuideBook.USER.exceptions.PaymentOrderSaveFailedException;
import com.guidebook.GuideBook.USER.exceptions.SubscriptionOrderSaveFailedException;
import com.razorpay.Order;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentOrderService {
    private final PaymentOrderRepository paymentOrderRepository;
    private final SubscriptionOrderRepository subscriptionOrderRepository;
    private final MyUserService myUserService;
    private final StudentMentorAccountService studentMentorAccountService;
    private final ClientAccountService clientAccountService;
    @Autowired
    public PaymentOrderService(PaymentOrderRepository paymentOrderRepository,
                               SubscriptionOrderRepository subscriptionOrderRepository,
                               MyUserService myUserService,
                               StudentMentorAccountService studentMentorAccountService,
                               ClientAccountService clientAccountService) {
        this.paymentOrderRepository = paymentOrderRepository;
        this.subscriptionOrderRepository = subscriptionOrderRepository;
        this.myUserService = myUserService;
        this.studentMentorAccountService = studentMentorAccountService;
        this.clientAccountService = clientAccountService;
    }

//    @Transactional
//    public void addPaymentOrder(Order order, String userEmail)
//            throws PaymentOrderSaveFailedException {
//
//        try {
//            PaymentOrder paymentOrder = new PaymentOrder();
//
//            // Convert the Order object to a JSON object for easier manipulation
//            JSONObject orderJson = new JSONObject(order.toString());
//
//            // Safely retrieve values from the JSON object
//            String orderId = orderJson.getString("id");
//            int amount = orderJson.getInt("amount");
//            String currency = orderJson.getString("currency");
//            String receipt = orderJson.getString("receipt");
//            String status = orderJson.getString("status");
//            String createdAt = String.valueOf(orderJson.getLong("created_at"));
//
//            // Get the nested notes JSON object
//            JSONObject notes = orderJson.getJSONObject("notes");
//            String subscriptionAmount = notes.optString("subscription_amount", String.valueOf(amount));
//            String gpayPhone = notes.optString("gpay_phone", "");
//            String customerUsername = notes.optString("customer_username", "");
//            String subscriptionType = notes.optString("subscription_type", "");
//
////            notes.put("customer_email", userEmail); // Storing user email in notes
////            notes.put("gpay_phone", ""); // Placeholder for GPay/Phone number. Add the actual number if available.
////            notes.put("Zoom Session Duration(min)", transaction.getZoomSessionForm().getZoomSessionDurationInMin()); // You can add more user-specific info, like the subscription type
////            notes.put("Zoom Session Amount paid", amt);
//
//            // Set values in the new SubscriptionOrder entity
//            newOrder.setSubscriptionRzpOrderId(orderId);
//            newOrder.setSubscriptionAmount(subscriptionAmount);
//            newOrder.setSubscriptionUserEmail(userEmail);
//            newOrder.setSubscriptionUserGpayNumber(gpayPhone);
//            newOrder.setSubscriptionUserName(customerUsername);
//            newOrder.setSubscriptionPlan(subscriptionType);
//            newOrder.setCreatedAt(createdAt);
//            newOrder.setSubscriptionCurrency(currency);
//            newOrder.setSubscriptionReceipt(receipt);
//            newOrder.setSubscriptionStatus(status);
//            newOrder.setSubscriptionUserEmailAccountType(
//                    myUserService.checkUserEmailAccountTypeGeneralPurpose(userEmail)
//            );
//
//            // Save the order in the repository
//            subscriptionOrderRepository.save(newOrder);
//
//        } catch (Exception e) {
//            // Handle and log the exception
//            throw new SubscriptionOrderSaveFailedException("Failed to save the subscription order at addSubscriptionOrder() method. Rzp orderId = " + order.get("id"));
//        }
//    }
}
