package com.guidebook.GuideBook.USER.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetStudentMentorProfileAccountDetailsRequest {
    @NotNull
    private String studentMentorEmail;
}
