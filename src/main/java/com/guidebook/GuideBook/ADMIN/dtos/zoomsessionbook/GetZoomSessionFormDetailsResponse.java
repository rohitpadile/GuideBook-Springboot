package com.guidebook.GuideBook.ADMIN.dtos.zoomsessionbook;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@Builder
public class GetZoomSessionFormDetailsResponse {

    private String clientFirstName;
    private String clientMiddleName;
    private String clientLastName;
    private String clientEmail;
    private String clientPhoneNumber;
    private Integer clientAge;
    private String clientCollege;
    private String clientProofDocLink;
    private Integer isVerified;
    private String clientFeedbackFormLink;
    private Date createdOn;
    private String bookStatus;

    private Long zoomSessionDurationInMin;
    private String zoomSessionClientGoals;
    private String zoomSessionClientExpectations;
//    private String //SEND FEEDBACK FORM LINK
}
