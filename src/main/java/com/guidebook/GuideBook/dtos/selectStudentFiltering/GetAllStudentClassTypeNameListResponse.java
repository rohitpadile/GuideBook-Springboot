package com.guidebook.GuideBook.dtos.selectStudentFiltering;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Valid
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetAllStudentClassTypeNameListResponse {
    List<String> allStudentClassTypeNamesList;
}
