package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.StudentProfile;
import com.guidebook.GuideBook.Services.StudentProfileService;
import com.guidebook.GuideBook.dtos.AddStudentProfileRequest;
import com.guidebook.GuideBook.dtos.GetStudentProfileResponse;
import com.guidebook.GuideBook.dtos.UpdateStudentProfileRequest;
import com.guidebook.GuideBook.exceptions.StudentProfileContentNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

//@CrossOrigin(origins = "http://guidebookx.s3-website.ap-south-1.amazonaws.com")
//@CrossOrigin(origins = {"http://localhost:3000/", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
public class StudentProfileController {
    private StudentProfileService studentProfileService;
    @Autowired
    public StudentProfileController(StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    @PostMapping("/addStudentProfile")
    public ResponseEntity<GetStudentProfileResponse> addStudentProfile(@RequestBody AddStudentProfileRequest request)
    throws StudentProfileContentNotFoundException{
        GetStudentProfileResponse response = studentProfileService.addStudentProfile(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/studentProfile/{studentWorkEmail}")
    public ResponseEntity<GetStudentProfileResponse> getStudentProfile(@PathVariable String studentWorkEmail)
            throws StudentProfileContentNotFoundException
    {
        GetStudentProfileResponse res = studentProfileService.getStudentProfile(studentWorkEmail);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/updateStudentProfile/{studentWorkEmail}")
    public ResponseEntity<GetStudentProfileResponse> updateStudentProfile(
            @PathVariable String studentWorkEmail,
            @RequestBody UpdateStudentProfileRequest updateRequest)
    throws StudentProfileContentNotFoundException
    {
        GetStudentProfileResponse response = studentProfileService
                .updateStudentProfile(studentWorkEmail, updateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
