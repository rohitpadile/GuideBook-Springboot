package com.guidebook.GuideBook.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetStudentBasicDetailsResponse {
    String branch;
    Double cetPercentile;
    Double grade;
    String classType;
    List<String> languagesSpoken = new ArrayList<>();
    String publicEmail;
    String category;
    String college;
    String examName;
}
