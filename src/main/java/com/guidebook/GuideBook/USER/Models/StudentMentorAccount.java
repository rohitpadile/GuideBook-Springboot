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
}
