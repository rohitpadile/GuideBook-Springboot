package com.guidebook.GuideBook.TR.Controller;

import com.guidebook.GuideBook.ADMIN.Controller.StudentCategoryController;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllStudentCategoryNameListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class TRStudentCategoryController {
    private StudentCategoryController studentCategoryController;
    @Autowired
    public TRStudentCategoryController(StudentCategoryController studentCategoryController) {
        this.studentCategoryController = studentCategoryController;
    }

    @GetMapping("/getAllStudentCategory")
    public ResponseEntity<GetAllStudentCategoryNameListResponse> getStudentCategoryList(){
        return studentCategoryController.getStudentCategoryList();
    }
}
