package com.guidebook.GuideBook.MEETHOST.dtos;

import lombok.Data;

@Data
public class UpdateEventRequest {
    private String eventId;
    private String eventName;
    private String eventType;//use enum.toString
    private String eventDescription;
    private String eventLocation;
    private String registrationLink;
    private String organizer;
    private String zoomLink;
    private String dateAndTime;

}
