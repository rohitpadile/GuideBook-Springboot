package com.guidebook.GuideBook.ADMIN.Controller;

import com.guidebook.GuideBook.ADMIN.Models.StudentCategory;
import com.guidebook.GuideBook.ADMIN.Services.StudentCategoryService;
import com.guidebook.GuideBook.ADMIN.dtos.AddStudentCategoryRequest;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllStudentCategoryNameListResponse;
import com.guidebook.GuideBook.ADMIN.exceptions.AlreadyPresentException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
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
    public ResponseEntity<StudentCategory> addStudentCategory(
            @RequestBody @Valid AddStudentCategoryRequest addStudentCategoryRequest)
            throws AlreadyPresentException {
        StudentCategory studentCategoryAdded = studentCategoryService.addStudentCategory(addStudentCategoryRequest);
        return new ResponseEntity<>(studentCategoryAdded , HttpStatus.CREATED);
    }
}
