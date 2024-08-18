package com.guidebook.GuideBook.ADMIN.dtos;

import lombok.Data;

@Data
public class UpdateStudentProfileSessionsPerWeekRequest {
    private Integer newSessionsPerWeekValue;
    private Integer newSessionsRemainingPerWeekValue;
}
