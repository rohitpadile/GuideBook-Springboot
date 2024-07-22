package com.guidebook.GuideBook.dtos.zoomsessionbook;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
public class ConfirmZoomSessionRequestFromStudent {
    private String studentWorkEmail;
    private String ZoomSessionFormId;
    private String zoomSessionFeedbackCode; //this code determines the form id and studentWorkEmail
    //this code helps in tracking which sessions had successful feedbacks and associated student details and client details(Or form details)
    private String zoomSessionFeedbackGoogleForm; //optional property
    //convert the google form into csv, from feedback code, make a list of associated studentWorkEmails.
    //Also track how many sessions those students have conducted
}
