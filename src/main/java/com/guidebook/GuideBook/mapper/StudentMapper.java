package com.guidebook.GuideBook.mapper;


import com.guidebook.GuideBook.Models.Student;

import com.guidebook.GuideBook.dtos.AddStudentRequest;

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
