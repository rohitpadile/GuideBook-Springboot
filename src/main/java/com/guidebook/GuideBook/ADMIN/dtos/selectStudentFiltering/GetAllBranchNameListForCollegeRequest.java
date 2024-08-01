package com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Valid
public class GetAllBranchNameListForCollegeRequest {
    @NotNull(message = "College name should be present to fetch branches for that college in the drop-down list")
    private String collegeName;
}
