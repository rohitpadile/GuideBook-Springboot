package com.guidebook.GuideBook.dtos.zoomsessionbook;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
public class SubmitZoomSessionFeedbackFormRequest {
    private String zoomSessionTransactionId;
    @NotNull
    private String overallFeedback; //this will mark the session counted in the students account
    //optional fields
    private String purposeFulfilled;
    private String moreFeedbackAboutStudent;
    private String feedbackForCompany;
}
