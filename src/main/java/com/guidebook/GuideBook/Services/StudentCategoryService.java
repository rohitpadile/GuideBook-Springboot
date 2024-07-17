package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.StudentCategory;
import com.guidebook.GuideBook.Repository.StudentCategoryRepository;
import com.guidebook.GuideBook.dtos.AddStudentCategoryRequest;
import com.guidebook.GuideBook.dtos.selectStudentFiltering.GetAllStudentCategoryNameListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public StudentCategory getStudentCategoryByStudentCategoryNameIgnoreCase(String name){
        return studentCategoryRepository.findStudentCategoryByStudentCategoryNameIgnoreCase(name);
    }

    public StudentCategory addStudentCategory(AddStudentCategoryRequest addStudentCategoryRequest) {
        StudentCategory newStudentCategory = new StudentCategory();
        newStudentCategory.setStudentCategoryName(addStudentCategoryRequest.getStudentCategory());
        return studentCategoryRepository.save(newStudentCategory);
    }
}
