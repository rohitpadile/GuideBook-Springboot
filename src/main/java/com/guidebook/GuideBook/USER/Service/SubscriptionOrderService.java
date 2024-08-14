package com.guidebook.GuideBook.USER.Service;

import com.guidebook.GuideBook.USER.Models.SubscriptionOrder;
import com.guidebook.GuideBook.USER.Repository.SubscriptionOrderRepository;
import com.guidebook.GuideBook.USER.exceptions.SubscriptionOrderSaveFailedException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.razorpay.*;
@Service
public class SubscriptionOrderService {
    private final SubscriptionOrderRepository subscriptionOrderRepository;

    @Autowired
    public SubscriptionOrderService(SubscriptionOrderRepository subscriptionOrderRepository) {
        this.subscriptionOrderRepository = subscriptionOrderRepository;
    }

    @Transactional
    public void addSubscriptionOrder(Order order, String userEmail)
            throws SubscriptionOrderSaveFailedException {
        SubscriptionOrder newOrder = new SubscriptionOrder();
        newOrder.setSubscriptionRzpOrderId(order.get("id"));
        newOrder.setSubscriptionAmount(order.get("amount").toString());
        newOrder.setSubscriptionUserEmail(userEmail);
        newOrder.setSubscriptionUserGpayNumber(order.get("notes.gpay_phone").toString());
        newOrder.setSubscriptionUserName(order.get("notes.customer_username"));
        newOrder.setSubscriptionPlan(order.get("notes.subscription_type"));
        newOrder.setCreatedAt(order.get("created_at"));
        newOrder.setSubscriptionCurrency(order.get("currency"));
        newOrder.setSubscriptionReceipt(order.get("receipt"));
        newOrder.setSubscriptionStatus(order.get("status"));
        subscriptionOrderRepository.save(newOrder);
    }
}
