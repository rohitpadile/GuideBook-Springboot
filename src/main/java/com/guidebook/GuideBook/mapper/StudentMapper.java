package com.guidebook.GuideBook.mapper;


import com.guidebook.GuideBook.Models.Student;

import com.guidebook.GuideBook.dtos.AddStudentRequest;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
@Builder
@UtilityClass
public class StudentMapper {
    public static Student mapToStudent(AddStudentRequest request){
        return Student.builder()
                .studentName(request.getStudentName())
                .studentMis(request.getStudentMis())
                .cetPercentile(request.getStudentCetPercentile())
                .grade(request.getStudentGrade())
                .studentClassType(request.getStudentClassType())
                .build();
    }
}
