package com.guidebook.GuideBook.ADMIN.dtos.zoomsessionbook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Valid
public class ConfirmZoomSessionFromStudentRequest { //DTO about the confirmation to both student and client
    @NotNull
    private String studentWorkEmail; //fetched from encrypted url
    @NotNull
    private String ZoomSessionFormId;// fetched from encrypted url
    @NotNull
    private Integer isAvailable;
    private String zoomSessionTime;
    private String zoomSessionMeetingId;
    private String zoomSessionPasscode;
    private String zoomSessionMeetingLink;

    private String studentMessageToClient; //When not available. + when available(optional).

}
