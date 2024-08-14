package com.guidebook.GuideBook.USER.Service;

import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import com.guidebook.GuideBook.USER.Models.SubscriptionOrder;
import com.guidebook.GuideBook.USER.Repository.SubscriptionOrderRepository;
import com.guidebook.GuideBook.USER.dtos.SubscriptionOrderPaymentSuccessRequest;
import com.guidebook.GuideBook.USER.exceptions.SubscriptionActivationFailedException;
import com.guidebook.GuideBook.USER.exceptions.SubscriptionOrderNotFoundException;
import com.guidebook.GuideBook.USER.exceptions.SubscriptionOrderSaveFailedException;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.razorpay.*;
@Service
public class SubscriptionOrderService {
    private final SubscriptionOrderRepository subscriptionOrderRepository;
    private final MyUserService myUserService;
    private final StudentMentorAccountService studentMentorAccountService;
    private final ClientAccountService clientAccountService;
    @Autowired
    public SubscriptionOrderService(SubscriptionOrderRepository subscriptionOrderRepository,
                                    MyUserService myUserService,
                                    ClientAccountService clientAccountService,
                                    StudentMentorAccountService studentMentorAccountService) {
        this.subscriptionOrderRepository = subscriptionOrderRepository;
        this.myUserService = myUserService;
        this.studentMentorAccountService = studentMentorAccountService;
        this.clientAccountService = clientAccountService;
    }

    @Transactional
    public void addSubscriptionOrder(Order order, String userEmail)
            throws SubscriptionOrderSaveFailedException {

        try {
            SubscriptionOrder newOrder = new SubscriptionOrder();

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
            String subscriptionAmount = notes.optString("subscription_amount", String.valueOf(amount));
            String gpayPhone = notes.optString("gpay_phone", "");
            String customerUsername = notes.optString("customer_username", "");
            String subscriptionType = notes.optString("subscription_type", "");

            // Set values in the new SubscriptionOrder entity
            newOrder.setSubscriptionRzpOrderId(orderId);
            newOrder.setSubscriptionAmount(subscriptionAmount);
            newOrder.setSubscriptionUserEmail(userEmail);
            newOrder.setSubscriptionUserGpayNumber(gpayPhone);
            newOrder.setSubscriptionUserName(customerUsername);
            newOrder.setSubscriptionPlan(subscriptionType);
            newOrder.setCreatedAt(createdAt);
            newOrder.setSubscriptionCurrency(currency);
            newOrder.setSubscriptionReceipt(receipt);
            newOrder.setSubscriptionStatus(status);

            // Save the order in the repository
            subscriptionOrderRepository.save(newOrder);

        } catch (Exception e) {
            // Handle and log the exception
            throw new SubscriptionOrderSaveFailedException("Failed to save the subscription order at addSubscriptionOrder() method. Rzp orderId = " + order.get("id"));
        }
    }
    @Transactional
    public void activateSubscriptionForUseremail(SubscriptionOrderPaymentSuccessRequest request, String userEmail)
            throws SubscriptionOrderNotFoundException,
            SubscriptionActivationFailedException {
        SubscriptionOrder order = subscriptionOrderRepository.findBySubscriptionRzpOrderId(request.getSubscriptionRzpOrderId());
        if(order != null){
            order.setSubscriptionStatus("paid");
            order.setSubscriptionPaymentId(request.getSubscriptionPaymentId());
            //activate subscription of user
            this.activateSubscriptionForUseremail(userEmail, request.getSubPlan());
        } else{
            throw new SubscriptionOrderNotFoundException("Subscription order not found for order id: " +
                    request.getSubscriptionRzpOrderId() + " at activateSubscription() method");
        }
    }
    @Transactional
    private void activateSubscriptionForUseremail(String userEmail, String subPlan)
            throws SubscriptionActivationFailedException {

        if((myUserService.checkUserEmailAccountTypeGeneralPurpose(userEmail)) == 1){
            StudentMentorAccount acc = studentMentorAccountService.getAccountByEmail(userEmail);
            if(subPlan.equalsIgnoreCase("monthly")){
                acc.setStudentMentorAccountSubscription_Monthly(1); // 1 = enable
            }
            //else if for other subscription types in future

        } else if((myUserService.checkUserEmailAccountTypeGeneralPurpose(userEmail)) == 2){
            ClientAccount acc = clientAccountService.getAccountByEmail(userEmail);
            if(subPlan.equalsIgnoreCase("monthly")){
                acc.setClientAccountSubscription_Monthly(1); //1 = enable
            }

        } else {
            throw new SubscriptionActivationFailedException("For user email: " + userEmail);
        }
    }

}
