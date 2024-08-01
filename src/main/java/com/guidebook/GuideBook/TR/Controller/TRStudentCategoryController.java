package com.guidebook.GuideBook.TR.Controller;

import com.guidebook.GuideBook.ADMIN.Controller.StudentCategoryController;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllStudentCategoryNameListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/teamRecruiter/")
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
