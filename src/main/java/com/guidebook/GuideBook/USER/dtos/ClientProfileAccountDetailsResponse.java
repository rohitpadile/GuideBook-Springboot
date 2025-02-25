package com.guidebook.GuideBook.USER.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientProfileAccountDetailsResponse {
    private String clientAccountEmail;
    private Long clientAccountZoomSessionCount;
    private Long clientAccountOfflineSessionCount;
    private Integer clientAccountSubscription_Monthly; //1 or 0 will determine if the monthly subscription is active or not
    private String clientFirstName;
    private String clientMiddleName;
    private String clientLastName;
    private String clientPhoneNumber;
    private Integer clientAge;
    private String clientCollege;
    private String clientValidProof;
    private String clientZoomEmail;
}
