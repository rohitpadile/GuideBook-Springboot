package com.guidebook.GuideBook.Models;

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
    @Column(unique = true)
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

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Version
    private Integer version;
}
