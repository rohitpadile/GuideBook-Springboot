package com.guidebook.GuideBook.USER.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String subscriptionOrderId;

    private String subscriptionAmount;
    private String subscriptionUserEmail;
    private String subscriptionUserGpayNumber;
    private String subscriptionUserName;
    private String subscriptionPlan;
    private String createdAt;
    private String subscriptionCurrency;
    private String subscriptionReceipt;
    private String subscriptionRzpOrderId;
    private String subscriptionStatus;

    private String subscriptionPaymentId;

}
