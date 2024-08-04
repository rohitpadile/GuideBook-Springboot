package com.guidebook.GuideBook.ADMIN.Controller;

import com.guidebook.GuideBook.ADMIN.dtos.UpdateStudentProfileRequest;
import com.guidebook.GuideBook.ADMIN.exceptions.StudentProfileContentNotFoundException;
import com.guidebook.GuideBook.ADMIN.Services.StudentProfileService;
import com.guidebook.GuideBook.ADMIN.dtos.AddStudentProfileRequest;
import com.guidebook.GuideBook.ADMIN.dtos.GetStudentProfileResponse;
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
public class StudentProfileController {
    private StudentProfileService studentProfileService;
    @Autowired
    public StudentProfileController(StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    @PostMapping("/addStudentProfile")
    public ResponseEntity<GetStudentProfileResponse> addStudentProfile(@RequestBody AddStudentProfileRequest request)
    throws StudentProfileContentNotFoundException {
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
