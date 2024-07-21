package com.guidebook.GuideBook.dtos.zoomsessionform;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ZoomSessionOTPResendRequest {
    private String ZoomSessionFormId;
}
