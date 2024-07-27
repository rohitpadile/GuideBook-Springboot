package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Models.StudentClassType;
import com.guidebook.GuideBook.Repository.StudentClassTypeRepository;
import com.guidebook.GuideBook.dtos.AddStudentClassTypeRequest;
import com.guidebook.GuideBook.dtos.selectStudentFiltering.GetAllStudentClassTypeNameListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentClassTypeService {

    private StudentClassTypeRepository studentClassTypeRepository;

    @Autowired
    public StudentClassTypeService(StudentClassTypeRepository studentClassTypeRepository) {
        this.studentClassTypeRepository = studentClassTypeRepository;
    }

    public StudentClassType getStudentClassTypeByStudentClassTypeName(String classType) {
        return studentClassTypeRepository.findStudentClassTypeByStudentClassTypeName(classType);
    }

    public StudentClassType addStudentClassType(AddStudentClassTypeRequest addStudentClassTypeRequest) {
        StudentClassType studentClassType = new StudentClassType();
        studentClassType.setStudentClassTypeName(addStudentClassTypeRequest.getStudentClassTypeName());
        return studentClassTypeRepository.save(studentClassType);
    }

    public GetAllStudentClassTypeNameListResponse getAllStudentClassTypes() {
        GetAllStudentClassTypeNameListResponse response = new GetAllStudentClassTypeNameListResponse();
        List<StudentClassType> classTypes = studentClassTypeRepository.findAll();

        for (StudentClassType classType : classTypes){
            response.getAllStudentClassTypeNamesList().add(classType.getStudentClassTypeName());
        }
        return response;
    }

    public List<String> getAllStudentClassTypeNameList(){
        List<String> result = new ArrayList<>();
        List<StudentClassType> studentClassTypes = studentClassTypeRepository.findAll();
        for(StudentClassType studentClassType : studentClassTypes){
            result.add(studentClassType.getStudentClassTypeName());
        }
        return result;
    }
}

