package com.guidebook.GuideBook.MENTORSERVICE.dtos;

import lombok.Data;

@Data
public class SendMailToAllMentorsRequestMail {
    private String mailContent;
    private String mailSubject;
}
