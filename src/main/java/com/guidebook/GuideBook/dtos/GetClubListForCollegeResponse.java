package com.guidebook.GuideBook.dtos;

import lombok.Data;

import java.util.List;
@Data
public class GetClubListForCollegeResponse {
    private List<String> collegeClubNameList;
}
