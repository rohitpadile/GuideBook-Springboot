package com.guidebook.GuideBook.dtos;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Valid
@Data
public class GetAllCollegeListForClubsResponse {
    private List<String> collegeNameList = new ArrayList<>();
}
