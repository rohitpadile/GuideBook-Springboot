package com.guidebook.GuideBook.ADMIN.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddHelpDeskEmailRequest {
    private String helpDeskFirstName;
    private String helpDeskLastName;
    private String helpDeskEmailSubject;
    private String helpDeskEmailSentFrom;
    private String helpDeskEmailContent;
}
