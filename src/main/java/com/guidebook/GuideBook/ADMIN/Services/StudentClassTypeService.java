package com.guidebook.GuideBook.ADMIN.Services;

import com.guidebook.GuideBook.ADMIN.Models.StudentClassType;
import com.guidebook.GuideBook.ADMIN.dtos.AddStudentClassTypeRequest;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllStudentClassTypeNameListResponse;
import com.guidebook.GuideBook.ADMIN.exceptions.AlreadyPresentException;
import com.guidebook.GuideBook.ADMIN.exceptions.StudentClassTypeNotFoundException;
import com.guidebook.GuideBook.ADMIN.Repository.StudentClassTypeRepository;
import jakarta.transaction.Transactional;
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

    public StudentClassType getStudentClassTypeByStudentClassTypeName(String classType)
    throws StudentClassTypeNotFoundException {
        return studentClassTypeRepository.findStudentClassTypeByStudentClassTypeNameIgnoreCase(classType);
    }
    @Transactional
    public StudentClassType addStudentClassType(AddStudentClassTypeRequest addStudentClassTypeRequest)
            throws AlreadyPresentException {
        if((studentClassTypeRepository.findStudentClassTypeByStudentClassTypeNameIgnoreCase(
                addStudentClassTypeRequest.getStudentClassTypeName()
        )) != null){
            throw new AlreadyPresentException("Student Class type already present at addStudentClassType() method: "  +
                    addStudentClassTypeRequest.getStudentClassTypeName());
        }
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

