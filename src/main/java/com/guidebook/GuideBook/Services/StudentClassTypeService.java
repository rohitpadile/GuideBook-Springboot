package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Models.StudentClassType;
import com.guidebook.GuideBook.Repository.StudentClassTypeRepository;
import com.guidebook.GuideBook.dtos.AddStudentClassTypeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentClassTypeService {

    private StudentClassTypeRepository studentClassTypeRepository;

    @Autowired
    public StudentClassTypeService(StudentClassTypeRepository studentClassTypeRepository) {
        this.studentClassTypeRepository = studentClassTypeRepository;
    }
    public StudentClassType getStudentClassTypeByStudentClassTypeName(String classType){
        return studentClassTypeRepository.findStudentClassTypeByStudentClassTypeName(classType);
    }

    public StudentClassType addStudentClassType(AddStudentClassTypeRequest addStudentClassTypeRequest) {
        StudentClassType studentClassType = new StudentClassType();
        studentClassType.setStudentClassTypeName(addStudentClassTypeRequest.getStudentClassTypeName());
        return studentClassTypeRepository.save(studentClassType);
    }
}

