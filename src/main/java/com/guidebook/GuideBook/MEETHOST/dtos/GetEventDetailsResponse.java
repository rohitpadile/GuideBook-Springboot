package com.guidebook.GuideBook.MEETHOST.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetEventDetailsResponse {
    private String eventName;
    private String eventType; // Use enum.toString()
    private String eventDescription;
    private String eventLocation;
    private String registrationLink;
    private Integer isActive;
    private String organizer;
    private String zoomLink;
    private String dateAndTime;
    private String eventCode;

}
