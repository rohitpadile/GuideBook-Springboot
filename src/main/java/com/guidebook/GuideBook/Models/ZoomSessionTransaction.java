package com.guidebook.GuideBook.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoomSessionTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String zoomSessionTransactionId;

    private String studentWorkEmail;
    private String zoomSessionFormId;
    private String zoomSessionFeedbackFormId;

    private Double transactionAmount;

}
