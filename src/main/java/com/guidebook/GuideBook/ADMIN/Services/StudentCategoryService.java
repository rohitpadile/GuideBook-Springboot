package com.guidebook.GuideBook.ADMIN.Services;

import com.guidebook.GuideBook.ADMIN.exceptions.StudentCategoryNotFoundException;
import com.guidebook.GuideBook.ADMIN.Models.StudentCategory;
import com.guidebook.GuideBook.ADMIN.Repository.StudentCategoryRepository;
import com.guidebook.GuideBook.ADMIN.dtos.AddStudentCategoryRequest;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllStudentCategoryNameListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentCategoryService {
    private StudentCategoryRepository studentCategoryRepository;
    @Autowired
    public StudentCategoryService(StudentCategoryRepository studentCategoryRepository) {
        this.studentCategoryRepository = studentCategoryRepository;
    }

    public GetAllStudentCategoryNameListResponse getStudentCategoryList() {
        GetAllStudentCategoryNameListResponse response = new GetAllStudentCategoryNameListResponse();
        List<StudentCategory> studentCategoryList = studentCategoryRepository.findAll();
        for(StudentCategory studentCategory : studentCategoryList){
            response.getAllStudentCategoryNamesList().add(
                    studentCategory.getStudentCategoryName()
            );
        }
        return response;
    }

    public StudentCategory getStudentCategoryByStudentCategoryNameIgnoreCase(String name)
    throws StudentCategoryNotFoundException {
        return studentCategoryRepository.findStudentCategoryByStudentCategoryNameIgnoreCase(name);
    }

    public StudentCategory addStudentCategory(AddStudentCategoryRequest addStudentCategoryRequest) {
        StudentCategory newStudentCategory = new StudentCategory();
        newStudentCategory.setStudentCategoryName(addStudentCategoryRequest.getStudentCategory());
        return studentCategoryRepository.save(newStudentCategory);
    }

    public List<String> getAllStudentCategoryNameList(){
        List<String> result = new ArrayList<>();
        List<StudentCategory> studentCategoryList = studentCategoryRepository.findAll();
        for(StudentCategory studentCategory : studentCategoryList){
            result.add(studentCategory.getStudentCategoryName());
        }
        return result;
    }
}

