package com.guidebook.GuideBook.USER.Controller;

import com.guidebook.GuideBook.ADMIN.exceptions.StudentProfileContentNotFoundException;
import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import com.guidebook.GuideBook.USER.Service.JwtUtil;
import com.guidebook.GuideBook.USER.Service.StudentMentorAccountService;
import com.guidebook.GuideBook.USER.dtos.EditMentorAccountRequest;
import com.guidebook.GuideBook.USER.exceptions.StudentMentorAccountNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com", "https://a.guidebookx.com",
        "https://13.235.131.222",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/user/")
public class StudentMentorAccountController {
    private final StudentMentorAccountService studentMentorAccountService;
    private final JwtUtil jwtUtil;
    @Autowired
    public StudentMentorAccountController(StudentMentorAccountService studentMentorAccountService,
                                          JwtUtil jwtUtil) {
        this.studentMentorAccountService = studentMentorAccountService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/editStudentMentorAccountDetails")
    public ResponseEntity<Void> editStudentMentorAccountDetails(
            @RequestBody EditMentorAccountRequest editStudentMentorAccountRequest, HttpServletRequest request
    ) throws StudentMentorAccountNotFoundException,
            StudentProfileContentNotFoundException {
        String studentMentorEmail = jwtUtil.extractEmailFromToken(request);
        studentMentorAccountService.editStudentMentorAccountDetails(editStudentMentorAccountRequest, studentMentorEmail);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/allStudentMentorAccounts") //for personal use
    public ResponseEntity<List<StudentMentorAccount>> getAllStudentMentorAccounts(){
        return new ResponseEntity<>(studentMentorAccountService.getAllStudentMentorAccounts(), HttpStatus.OK);
    }

}
