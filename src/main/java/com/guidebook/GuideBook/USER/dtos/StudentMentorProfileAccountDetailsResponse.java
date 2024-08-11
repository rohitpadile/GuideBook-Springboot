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
}
