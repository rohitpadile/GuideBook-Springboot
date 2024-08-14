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
public class ClientAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String clientAccountId;

    @Column(unique = true, nullable = false)
    private String clientAccountEmail;

    private Long clientAccountZoomSessionCount;
    private Long clientAccountOfflineSessionCount;

    private Integer clientAccountSubscription_Monthly; //1 or 0 will determine if the monthly subscription is active or not
    // Subscription timestamps
    private Date subscriptionStartDate;
    private Date subscriptionEndDate;

    //BELOW FIELDS ARE SAME FOR BOTH THE ACCOUNTS (SAME NAMING ALSO) - CLIENT AND MENTOR ACCOUNT
    private String clientFirstName;
    private String clientMiddleName;
    private String clientLastName;
    private String clientPhoneNumber;
    private Integer clientAge;
    private String clientCollege;
    private String clientValidProof;
    private String clientZoomEmail;

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
