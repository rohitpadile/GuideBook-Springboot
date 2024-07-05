package com.guidebook.GuideBook.dtos;

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
}
