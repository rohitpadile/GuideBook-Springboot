package com.guidebook.GuideBook.ADMIN.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CancellationStatusZoomSessionViaTransactionIdRequest {
    @NotNull
    private String zoomSessionTransactionId;
}
