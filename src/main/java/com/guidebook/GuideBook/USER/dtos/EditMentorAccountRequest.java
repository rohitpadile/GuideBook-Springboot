package com.guidebook.GuideBook.USER.dtos;

import lombok.Data;

@Data
public class EditMentorAccountRequest {
    private String clientFirstName;
    private String clientMiddleName;
    private String clientLastName;
    private String clientPhoneNumber;
    private Integer clientAge;
    private String clientCollege;
    private String clientValidProof;
    private String clientZoomEmail;

    private Integer zoomSessionsPerWeek;
    private Integer zoomSessionsRemainingPerWeek;
}
