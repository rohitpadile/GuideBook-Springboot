package com.guidebook.GuideBook.MEETHOST.dtos;

import lombok.Data;

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
    private String dateAndTime;
    private String eventCode; //This code is from my side - same across all the entities related to this event;
//    for ticket
    private String ticketDescription;
}
