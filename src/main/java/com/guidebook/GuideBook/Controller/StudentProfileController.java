package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.StudentProfile;
import com.guidebook.GuideBook.Services.StudentProfileService;
import com.guidebook.GuideBook.dtos.AddStudentProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000/", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
public class StudentProfileController {
    private StudentProfileService studentProfileService;
    @Autowired
    public StudentProfileController(StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    @PostMapping("/addStudentProfile")
    public ResponseEntity<StudentProfile> addStudentProfile(@RequestBody AddStudentProfileRequest request) {
        StudentProfile studentProfile = studentProfileService.addStudentProfile(request);
        return new ResponseEntity<>(studentProfile, HttpStatus.CREATED);
    }
}
