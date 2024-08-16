package com.guidebook.GuideBook.USER.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class CreateOrderPaymentZoomSessionRequest {
    @NotNull
    private String zoomSessionTransactionId;
}
