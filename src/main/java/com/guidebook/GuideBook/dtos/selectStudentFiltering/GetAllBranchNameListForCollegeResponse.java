package com.guidebook.GuideBook.dtos.selectStudentFiltering;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetAllBranchNameListForCollegeResponse {
    List<String> allBranchNamesForCollegeList = new ArrayList<>();
}
