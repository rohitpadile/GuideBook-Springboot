package com.guidebook.GuideBook.ADMIN.mapper;

import com.guidebook.GuideBook.ADMIN.Models.StudentProfile;
import com.guidebook.GuideBook.ADMIN.dtos.AddStudentRequest;
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
                .studentWorkEmail(request.getStudentWorkEmail())
                .studentMis(request.getStudentMis())
                .studentProfileSessionsConducted(0L)
                .zoomSessionsPerWeek(0)
                .zoomSessionsRemainingPerWeek(0)
                .build();
    }
}
