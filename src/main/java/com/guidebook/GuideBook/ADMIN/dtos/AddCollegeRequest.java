package com.guidebook.GuideBook.ADMIN.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Valid
@Data
public class AddCollegeRequest {
    @NotNull(message = "College name cannot be empty")
    private String collegeName;
    @NotNull(message = "Colleges must have at least one non null branch name")
    private List<String> branchNames;
    private Set<String> collegeEntranceExamNameSet = new HashSet<>();

}
