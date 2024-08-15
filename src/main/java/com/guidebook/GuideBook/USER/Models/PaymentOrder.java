//package com.guidebook.GuideBook.USER.Models;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.util.Date;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class PaymentOrder {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private String paymentOrderId;
//
//    private String paymentAmount; //impt
//    private String paymentUserEmail;
//    private Integer paymentUserEmailAccountType;//1 for mentor, 2 for client
//    private String paymentUserGpayNumber;
//    private String paymentUserName;
//    private String paymentCreatedAt;
//    private String paymentCurrency;
//    private String paymentReceipt; //impt
//    private String paymentRzpOrderId; //impt
//    private String paymentStatus;
//
//    private String paymentId; //impt
//
//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    @JsonIgnore
//    Date createdOn;
//    @UpdateTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    @JsonIgnore
//    Date updatedOn;
//    @Version
//    @JsonIgnore
//    private Integer version;
//}
