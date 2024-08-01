package com.guidebook.GuideBook.ADMIN.dtos.zoomsessionbook;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
public class GetSubmittionStatusForFeedbackFormResponse {
    private Integer isSubmitted; // 1 or none/0
}

