package com.guidebook.GuideBook.dtos;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Valid
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddStudentProfileRequest {
    String aboutSection;
    String studentCityOfCoaching;
    String studentScoreDetails;
}
