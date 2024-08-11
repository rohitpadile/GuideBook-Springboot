package com.guidebook.GuideBook.USER.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckUserEmailAccountTypeResponse {
    private Integer accountType;
    //1 for Student Mentor
    //2 for Client
    //0 for nothing
}
