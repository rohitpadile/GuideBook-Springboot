package com.guidebook.GuideBook.USER.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyUserWithTransactionResponse {
    private String zoomSessionDurationInMin;
    private String zoomSessionBookStatus;
    private String studentMentorName;
}
