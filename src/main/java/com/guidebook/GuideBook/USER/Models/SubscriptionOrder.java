package com.guidebook.GuideBook.USER.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String subscriptionOrderId;

    private String subscriptionAmount; //impt
    private String subscriptionUserEmail;
    private Integer subscriptionUserEmailAccountType;//1 for mentor, 2 for client
    private String subscriptionUserGpayNumber;
    private String subscriptionUserName;
    private String subscriptionPlan;
    private String createdAt;
    private String subscriptionCurrency;
    private String subscriptionReceipt; //impt
    private String subscriptionRzpOrderId; //impt
    private String subscriptionStatus;

    private String subscriptionPaymentId; //impt

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    Date createdOn;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    Date updatedOn;
    @Version
    @JsonIgnore
    private Integer version;
}
