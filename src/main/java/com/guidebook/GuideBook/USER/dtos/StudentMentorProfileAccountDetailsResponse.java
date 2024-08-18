package com.guidebook.GuideBook.USER.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class StudentMentorProfileAccountDetailsResponse {

    private String studentMentorAccountWorkEmail;
    private Integer studentMentorAccountSubscription_Monthly;
    private String editStudentProfileLink;
    private Long studentProfileSessionsConducted;
    private Long studentMentorAccountZoomSessionCount;
    private Long studentMentorAccountOfflineSessionCount;
    private String branch;
    private Double examScore;
    private Double grade;
    private String yearOfStudy;
    @Builder.Default
    private List<String> languagesSpoken = new ArrayList<>();
    private String publicEmail;
    private String college;
//    String examName;
    private String studentName;
//    Long studentMis;

    private Integer zoomSessionsPerWeek;
    private Integer zoomSessionsRemainingPerWeek;

    //Student mentor as a client also
    private String clientFirstName;
    private String clientMiddleName;
    private String clientLastName;
    private String clientPhoneNumber;
    private Integer clientAge;
    private String clientValidProof; //This will be link from company that verifies he/she is a student mentor
    private String clientZoomEmail;
}
