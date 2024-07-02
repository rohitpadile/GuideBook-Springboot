package com.guidebook.GuideBook.dtos.selectStudentFiltering;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data

@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetAllLanguageNameListResponse {
    List<String> allLanguageNamesList = new ArrayList<>();
}
