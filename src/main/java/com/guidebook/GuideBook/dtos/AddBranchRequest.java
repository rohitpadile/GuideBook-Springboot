package com.guidebook.GuideBook.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Valid
public class AddBranchRequest {
    //we are adding set of branches to college in addCollege DTO , so no need to add set of colleges here.
    //This conclusion is fine for me.
    //We will see later that if we need to fetch the colleges in which a certain branch is there.
    //Then we need to add accordingly then. But that's rare the case.
    @NotNull(message = "Branch name cannot be empty")
    private String branchName;
}
