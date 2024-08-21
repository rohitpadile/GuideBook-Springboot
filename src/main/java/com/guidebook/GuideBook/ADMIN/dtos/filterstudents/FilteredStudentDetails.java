package com.guidebook.GuideBook.ADMIN.dtos.filterstudents;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilteredStudentDetails {
    String studentName;
    String studentWorkEmail;

    String studentCollegeName;
    String studentBranch;
    String cetPercentile;
}
