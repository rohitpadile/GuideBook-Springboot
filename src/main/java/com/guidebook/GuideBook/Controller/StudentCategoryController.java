package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Services.StudentCategoryService;
import com.guidebook.GuideBook.dtos.selectStudentFiltering.GetAllStudentCategoryNameListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://guidebookx.s3-website.ap-south-1.amazonaws.com")
//@CrossOrigin(origins = {"http://localhost:3000/", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
public class StudentCategoryController {
    private StudentCategoryService studentCategoryService;
    @Autowired
    public StudentCategoryController(StudentCategoryService studentCategoryService) {
        this.studentCategoryService = studentCategoryService;
    }

    @GetMapping("/studentCategory")
    public ResponseEntity<GetAllStudentCategoryNameListResponse> getStudentCategoryList(){
        GetAllStudentCategoryNameListResponse res = studentCategoryService.getStudentCategoryList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
