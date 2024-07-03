package com.guidebook.GuideBook.mapper;

import com.guidebook.GuideBook.Models.StudentProfile;
import com.guidebook.GuideBook.dtos.AddStudentRequest;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
@UtilityClass
public class StudentProfileMapper {
    public static StudentProfile mapToStudentProfile(AddStudentRequest request) {
        return StudentProfile.builder()
                .studentMis(request.getStudentMis())
                .build();
    }
}
