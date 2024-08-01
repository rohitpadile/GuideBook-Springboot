package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Models.StudentClassType;
import com.guidebook.GuideBook.Services.BranchService;
import com.guidebook.GuideBook.Services.StudentClassTypeService;
import com.guidebook.GuideBook.dtos.AddStudentClassTypeRequest;
import com.guidebook.GuideBook.dtos.selectStudentFiltering.GetAllStudentClassTypeNameListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/admin/")
public class StudentClassTypeController {

    private StudentClassTypeService studentClassTypeService;

    @Autowired
    StudentClassTypeController(StudentClassTypeService studentClassTypeService){
        this.studentClassTypeService = studentClassTypeService;
    }

    @PostMapping("/addStudentClassType")
    public ResponseEntity<StudentClassType> addStudentClassType(@RequestBody AddStudentClassTypeRequest addStudentClassTypeRequest){
        StudentClassType studentClassType = studentClassTypeService.addStudentClassType(addStudentClassTypeRequest);
        return new ResponseEntity<>(studentClassType, HttpStatus.CREATED);
    }

    @GetMapping("/studentClassTypes")
    public ResponseEntity<GetAllStudentClassTypeNameListResponse> getAllStudentClassTypes(){
        GetAllStudentClassTypeNameListResponse res = studentClassTypeService.getAllStudentClassTypes();
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
