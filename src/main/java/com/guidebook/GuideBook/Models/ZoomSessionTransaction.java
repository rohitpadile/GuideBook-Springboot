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
public class ZoomSessionTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String zoomSessionTransactionId;

    @ManyToOne
    @JoinColumn(name = "fk_transactionId_clientEmail", referencedColumnName = "clientEmail")
    Client Client; //owning side

    @ManyToOne
    @JoinColumn(name = "fk_transactionId_studentWorkEmail", referencedColumnName = "studentWorkEmail")
    Student student; //owning side
    //STUDENT MIS IS COMPULSORY - BUT WORK EMAIL CAN CHANGE - AND WORK EMAIL IS UNIQUE
    //RESTRICT THE NUMBER OF TIMES WORK EMAIL IS CHANGED BY STUDENT
    String studentMis;

    Double transactionAmount; //RESEARCH FOR ITS DATA TYPE TO BE USED FOR LARGE SCALE APP
}
