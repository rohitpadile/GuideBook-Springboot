package com.guidebook.GuideBook.USER.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentMentorAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String studentMentorAccountId;

    @Column(unique = true, nullable = false)
    private String studentMentorAccountWorkEmail;
    //no. of sessions conducted take from the StudentProfile Model instead of incrementing here too. that will be burden
    //no of offline session too in the student profile model.

    private Integer studentMentorAccountSubscription_Monthly; //1 = enable, 0 = disable

    private Long studentMentorAccountZoomSessionCount;
    private Long studentMentorAccountOfflineSessionCount;

    //Student mentor as a client also
    //BELOW FIELDS ARE SAME FOR BOTH THE ACCOUNTS (SAME NAMING ALSO) - CLIENT AND MENTOR ACCOUNT
    private String clientFirstName;
    private String clientMiddleName;
    private String clientLastName;
    private String clientPhoneNumber;
    private Integer clientAge;
    private String clientCollege; //this is already verified by company when registering mentor
    private String clientValidProof; //This will be link from company that verifies he/she is a student mentor
    private String clientZoomEmail;
}
