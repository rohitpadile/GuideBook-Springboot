package com.guidebook.GuideBook.MEETHOST.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
//@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddNewEventRequest {
    private String eventName;
    private String eventType;//use enum.toString
    private String eventDescription;
    private String eventLocation;
    private String registrationLink;
    private String organizer;
    private String zoomLink;
}
