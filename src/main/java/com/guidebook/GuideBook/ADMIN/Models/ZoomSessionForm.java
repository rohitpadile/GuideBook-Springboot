package com.guidebook.GuideBook.ADMIN.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ZoomSessionForm {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String zoomSessionFormId;

    private String clientFirstName;
    private String clientMiddleName;
    private String clientLastName;
    @Column(nullable = false)
    private String clientEmail;

    @Pattern(regexp = "^\\+?[0-9. ()-]{6,20}$", message = "Invalid phone number")
    private String clientPhoneNumber;

    private Integer clientAge;
    private String clientCollege;

    private String clientProofDocLink;

    private String clientOTP; // OTP field
    private Integer clientOtpAttempts;
    @Temporal(TemporalType.TIMESTAMP)
    private Date clientOTPExpiration; // OTP expiration time

    private Integer isVerified;

//    @Enumerated(value = EnumType.STRING)
//    ZoomSessionBookStatus zoomSessionBookStatus;
    String zoomSessionBookStatus; //using to string from enums to work this errors out

    @CreationTimestamp //be default uses database server time
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Version
    private Integer version;

    @OneToOne(mappedBy = "zoomSessionForm")
    @JsonIgnore
    private ZoomSessionTransaction zoomSessionTransaction;

    private String userEmail;
    private Long zoomSessionDurationInMin;
    @Lob
    private String zoomSessionClientGoals;
    private String zoomSessionClientExpectations;
}
