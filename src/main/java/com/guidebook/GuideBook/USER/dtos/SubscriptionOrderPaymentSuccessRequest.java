package com.guidebook.GuideBook.USER.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionOrderPaymentSuccessRequest {
    @NotNull(message = "payment id cannot be null for a successful payment")
    private String subscriptionPaymentId;
    @NotNull
    private String subPlan;
    @NotNull
    private String subscriptionRzpOrderId;
}
