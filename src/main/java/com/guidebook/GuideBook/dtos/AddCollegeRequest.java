package com.guidebook.GuideBook.dtos;

import com.guidebook.GuideBook.Models.Branch;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Valid
@Data
public class AddCollegeRequest {
    @NotNull(message = "College name cannot be empty")
    private String collegeName;
    @NotNull(message = "Colleges must have at least one non null branch name")
    private List<String> branchNames;

}
