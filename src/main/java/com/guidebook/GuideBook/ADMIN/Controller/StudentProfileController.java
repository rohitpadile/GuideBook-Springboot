package com.guidebook.GuideBook.ADMIN.Controller;

import com.guidebook.GuideBook.ADMIN.dtos.UpdateStudentProfileRequest;
import com.guidebook.GuideBook.ADMIN.dtos.UpdateStudentProfileSessionsPerWeekRequest;
import com.guidebook.GuideBook.ADMIN.exceptions.StudentProfileContentNotFoundException;
import com.guidebook.GuideBook.ADMIN.Services.StudentProfileService;
import com.guidebook.GuideBook.ADMIN.dtos.AddStudentProfileRequest;
import com.guidebook.GuideBook.ADMIN.dtos.GetStudentProfileResponse;
import com.guidebook.GuideBook.USER.Service.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
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
@Slf4j
public class StudentProfileController {
    private StudentProfileService studentProfileService;
    private final JwtUtil jwtUtil;
    @Autowired
    public StudentProfileController(StudentProfileService studentProfileService,
                                    JwtUtil jwtUtil) {
        this.studentProfileService = studentProfileService;
        this.jwtUtil = jwtUtil;
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
    @GetMapping("/studentProfile")
    public ResponseEntity<GetStudentProfileResponse> getStudentProfile(HttpServletRequest request)
            throws StudentProfileContentNotFoundException
    {
        String userEmail = jwtUtil.extractEmailFromToken(request);
        log.info("Student work email : {}", userEmail);
        GetStudentProfileResponse res = studentProfileService.getStudentProfile(userEmail);
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

    @GetMapping("/updateStudentProfileSessionsPerWeek")
    public ResponseEntity<Void> updateStudentProfileSessionsPerWeek(
            @RequestBody @Valid UpdateStudentProfileSessionsPerWeekRequest updateRequest,
            HttpServletRequest request
            ) throws StudentProfileContentNotFoundException {
        String userEmail = jwtUtil.extractEmailFromToken(request);
        studentProfileService.updateStudentProfileSessionsPerWeek(updateRequest, userEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
