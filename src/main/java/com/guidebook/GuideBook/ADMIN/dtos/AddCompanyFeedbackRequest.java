package com.guidebook.GuideBook.ADMIN.dtos;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j

public class AddCompanyFeedbackRequest {
    private String companyFeedbackUserName;
    @NotNull
    private String companyFeedbackUserEmail;
    @NotNull
    private String companyFeedbackTextBoxContent;
}
