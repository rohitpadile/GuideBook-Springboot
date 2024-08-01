package com.guidebook.GuideBook.ADMIN.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoomSessionTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String zoomSessionTransactionId;
//    private String studentWorkEmail;
//    private String zoomSessionFormId;
//    private String zoomSessionFeedbackFormId;

    private Double transactionAmount;


    @ManyToOne
    @JoinColumn(name = "fk_studentWorkEmail", referencedColumnName = "studentWorkEmail")
    private Student student;

    @OneToOne //Do not cascade for sensitive handling reasons
    @JoinColumn(name = "fk_zoomSessionFormId", referencedColumnName = "zoomSessionFormId")
    private ZoomSessionForm zoomSessionForm;

    @OneToOne //Do not cascade for sensitive handling reasons
    @JoinColumn(name = "fk_zoomSessionFeedbackFormId", referencedColumnName = "zoomSessionFeedbackFormId")
    private ZoomSessionFeedbackForm zoomSessionFeedbackForm;

}
