package com.guidebook.GuideBook.ADMIN.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
//@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddMoreBranchesToCollegeRequest {
    private String collegeName;
    private List<String> branchNameList;
}
