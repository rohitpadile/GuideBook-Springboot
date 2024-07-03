package com.guidebook.GuideBook.dtos;

import com.guidebook.GuideBook.dtos.helperDtos.StudentDetails;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class FilteredStudentListResponse {
    private List<StudentDetails> studentDetailsList = new ArrayList<>();
//    StudentDetails in helperObjects
}
