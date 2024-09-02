package com.guidebook.GuideBook.MEETHOST.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentSucessForEventBookingRequest {
    @NotNull(message = "Order id cannot be null")
    private String rzpOrderId;
    @NotNull(message = "There is not payment without paymentId from razorpay")
    private String paymentId;
    @NotNull
    private String eventCode;
}
