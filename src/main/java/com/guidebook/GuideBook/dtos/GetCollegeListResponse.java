package com.guidebook.GuideBook.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Valid
@Data
public class GetCollegeListResponse {
    @NotNull(message = "College name is mandatory in the collegeList")
    private List<String> collegeNameList;
}
