package com.guidebook.GuideBook.USER.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IsUserAStudentMentorResponse {
    private Integer isUserAStudentMentor;
}
