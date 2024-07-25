package com.guidebook.GuideBook.Models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data //getters and setters
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ZoomSessionTransactionFree {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String zoomSessionTransactionFreeId;

    @ManyToOne
    @JoinColumn(name = "fk_transactionId_studentWorkEmail", referencedColumnName = "studentWorkEmail", nullable = false, unique = true)
    Student student; //owning side
    //STUDENT MIS IS COMPULSORY - BUT WORK EMAIL CAN CHANGE - AND WORK EMAIL IS UNIQUE
    //RESTRICT THE NUMBER OF TIMES WORK EMAIL IS CHANGED BY STUDENT

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_zoomSessionForm", referencedColumnName = "zoomSessionFormId")
    ZoomSessionForm zoomSessionForm;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_zoomSessionFeedbackForm", referencedColumnName = "zoomSessionFeedbackFormId")
    ZoomSessionFeedbackForm zoomSessionFeedbackForm;

    Double transactionAmount; //RESEARCH FOR ITS DATA TYPE TO BE USED FOR LARGE SCALE APP
}
