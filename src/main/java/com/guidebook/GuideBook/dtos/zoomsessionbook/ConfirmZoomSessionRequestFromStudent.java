package com.guidebook.GuideBook.dtos.zoomsessionbook;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
public class ConfirmZoomSessionRequestFromStudent { //DTO about the confirmation to both student and client
    private String studentWorkEmail; //fetched from encrypted url
    private String ZoomSessionFormId;// fetched from encrypted url
    private String zoomSessionTime;
    private String zoomSessionMeetingId;
    private String zoomSessionPasscode;
    private String zoomSessionMeetingLink;

    private String studentMessageToClient; //When not available. + when available(optional).
}
