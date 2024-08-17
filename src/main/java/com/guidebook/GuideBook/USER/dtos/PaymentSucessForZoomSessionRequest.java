package com.guidebook.GuideBook.USER.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentSucessForZoomSessionRequest {
    @NotNull(message = "Transaction id cannot be null")
    private String zoomSessionTransactionId;
    @NotNull(message = "There is not payment without paymentId from razorpay")
    private String paymentId;
}
