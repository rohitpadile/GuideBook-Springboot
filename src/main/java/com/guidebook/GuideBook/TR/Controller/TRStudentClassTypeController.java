package com.guidebook.GuideBook.TR.Controller;

import com.guidebook.GuideBook.ADMIN.Controller.StudentClassTypeController;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllStudentClassTypeNameListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com", "https://a.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/teamRecruiter/")
public class TRStudentClassTypeController {
    private StudentClassTypeController studentClassTypeController;
    @Autowired
    public TRStudentClassTypeController(StudentClassTypeController studentClassTypeController) {
        this.studentClassTypeController = studentClassTypeController;
    }

    @GetMapping("/getAllStudentClassTypes")
    public ResponseEntity<GetAllStudentClassTypeNameListResponse> getAllStudentClassTypes(){
        return studentClassTypeController.getAllStudentClassTypes();
    }
}
