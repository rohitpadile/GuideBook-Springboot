package com.guidebook.GuideBook.MEETHOST.dtos;

import lombok.Builder;
import lombok.Data;

@Data
public class TicketResponse {
    private String title;
    private String dateAndTime;
    private String eventLocation;
}
