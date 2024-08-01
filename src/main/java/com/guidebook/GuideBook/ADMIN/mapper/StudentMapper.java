package com.guidebook.GuideBook.ADMIN.mapper;


import com.guidebook.GuideBook.ADMIN.dtos.AddStudentRequest;
import com.guidebook.GuideBook.ADMIN.Models.Student;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
@UtilityClass
public class StudentMapper {
    public static Student mapToStudent(AddStudentRequest request){
        return Student.builder()
                .studentMis(request.getStudentMis()) //Mis - added on 5th July
                .studentWorkEmail(request.getStudentWorkEmail()) //work email - added on 5th July
                .studentName(request.getStudentName())
                .cetPercentile(request.getStudentCetPercentile())
                .grade(request.getStudentGrade())
                .build();
    }
}
