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
    String branch;
    Double examScore;
    Double grade;
    String yearOfStudy;
    List<String> languagesSpoken = new ArrayList<>();
    String publicEmail;
    String college;
//    String examName;
    String studentName;
//    Long studentMis;

    //Student mentor as a client also
    private String clientFirstName;
    private String clientMiddleName;
    private String clientLastName;
    private String clientPhoneNumber;
    private Integer clientAge;
    private String clientValidProof; //This will be link from company that verifies he/she is a student mentor
    private String clientZoomEmail;
}
