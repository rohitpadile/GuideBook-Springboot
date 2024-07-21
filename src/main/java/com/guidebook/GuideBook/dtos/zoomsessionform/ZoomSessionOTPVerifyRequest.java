package com.guidebook.GuideBook.dtos.zoomsessionform;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Valid
@Slf4j
@Data
public class ZoomSessionOTPVerifyRequest {
    @NotNull
    private Long clientOTP;
    @NotNull
    private String clientEmail;
    @NotNull
    private String ZoomSessionFormId;
}
