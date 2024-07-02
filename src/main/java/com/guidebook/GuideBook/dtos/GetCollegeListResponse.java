package com.guidebook.GuideBook.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Valid
@Data
public class GetCollegeListResponse {

    private List<String> collegeNameList = new ArrayList<>();
}
