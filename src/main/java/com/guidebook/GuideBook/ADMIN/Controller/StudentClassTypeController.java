package com.guidebook.GuideBook.ADMIN.Controller;

import com.guidebook.GuideBook.ADMIN.dtos.AddStudentClassTypeRequest;
import com.guidebook.GuideBook.ADMIN.dtos.selectStudentFiltering.GetAllStudentClassTypeNameListResponse;
import com.guidebook.GuideBook.ADMIN.Models.StudentClassType;
import com.guidebook.GuideBook.ADMIN.Services.StudentClassTypeService;
import com.guidebook.GuideBook.ADMIN.exceptions.AlreadyPresentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com", "https://a.guidebookx.com",
        "https://13.235.131.222",
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
    public ResponseEntity<StudentClassType> addStudentClassType(
            @RequestBody AddStudentClassTypeRequest addStudentClassTypeRequest)
            throws AlreadyPresentException {
        StudentClassType studentClassType = studentClassTypeService.addStudentClassType(addStudentClassTypeRequest);
        return new ResponseEntity<>(studentClassType, HttpStatus.CREATED);
    }

    @GetMapping("/studentClassTypes")
    public ResponseEntity<GetAllStudentClassTypeNameListResponse> getAllStudentClassTypes(){
        GetAllStudentClassTypeNameListResponse res = studentClassTypeService.getAllStudentClassTypes();
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
