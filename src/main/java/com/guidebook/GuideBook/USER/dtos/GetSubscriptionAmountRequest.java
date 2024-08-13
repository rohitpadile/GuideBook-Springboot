package com.guidebook.GuideBook.USER.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetSubscriptionAmountRequest {
    @NotNull(message = "Subscription plan cannot be null")
    private String subscriptionPlan;
}
