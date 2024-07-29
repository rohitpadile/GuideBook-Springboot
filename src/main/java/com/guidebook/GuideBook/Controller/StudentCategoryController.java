package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.StudentCategory;
import com.guidebook.GuideBook.Services.StudentCategoryService;
import com.guidebook.GuideBook.dtos.AddStudentCategoryRequest;
import com.guidebook.GuideBook.dtos.selectStudentFiltering.GetAllStudentCategoryNameListResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "https://api.guidebookx.com",
        "http://guidebookx.s3-website.ap-south-1.amazonaws.com",
        "https://guidebookx.s3-website.ap-south-1.amazonaws.com",
        "http://d23toh43udoeld.cloudfront.net",
        "https://d23toh43udoeld.cloudfront.net",
        "http://guidebookx.com",
        "https://guidebookx.com", "http://localhost:3000", "http://localhost:8080"})
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

    @PostMapping("/addStudentCategory")
    public ResponseEntity<StudentCategory> addStudentCategory(@RequestBody @Valid AddStudentCategoryRequest addStudentCategoryRequest){
        StudentCategory studentCategoryAdded = studentCategoryService.addStudentCategory(addStudentCategoryRequest);
        return new ResponseEntity<>(studentCategoryAdded , HttpStatus.CREATED);
    }
}
