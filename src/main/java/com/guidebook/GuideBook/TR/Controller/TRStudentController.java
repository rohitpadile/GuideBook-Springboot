package com.guidebook.GuideBook.TR.Controller;

import com.guidebook.GuideBook.ADMIN.Controller.StudentController;
import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.ADMIN.dtos.*;
import com.guidebook.GuideBook.ADMIN.exceptions.*;
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
@RequestMapping("/api/v1/teamRecruiter/")
public class TRStudentController {

    private StudentService studentService;
    private StudentController studentController;
    @Autowired
    TRStudentController(StudentService studentService,
                        StudentController studentController){
        this.studentService = studentService;
        this.studentController = studentController;
    }


    @PostMapping("/addStudent")
    public ResponseEntity<GetStudentBasicDetailsResponse> addStudent(@RequestBody @Valid AddStudentRequest addStudentRequest)
            throws CollegeNotFoundException,
            BranchNotFoundException,
            StudentClassTypeNotFoundException,
            StudentCategoryNotFoundException,
            LanguageNotFoundException,
            StudentProfileContentNotFoundException,
            EncryptionFailedException {
        return studentController.addStudent(addStudentRequest);
    }

    @GetMapping("/studentBasicDetails/{workemail}")
    public ResponseEntity<GetStudentBasicDetailsResponse> getStudentBasicDetails(@PathVariable String workemail)
            throws StudentBasicDetailsNotFoundException
    {
        return studentController.getStudentBasicDetails(workemail);
    }

    @PostMapping("/updateStudent")
    public ResponseEntity<GetStudentBasicDetailsResponse> updateStudent(@RequestBody @Valid UpdateStudentRequest updateStudentRequest)
            throws StudentClassTypeNotFoundException, CollegeNotFoundException, StudentCategoryNotFoundException, LanguageNotFoundException {
        return studentController.updateStudent(updateStudentRequest);
    }

    //ABOVE ARE THE SAME METHODS THAT ADMIN ALSO USE - LIKE ME

    //BELOW METHODS TO BE DEFINED ARE :-

    @PostMapping("/deactivateStudent")
    public ResponseEntity<Void> deactivateStudent(@RequestBody @Valid DeactivateStudentRequest deactivateStudentRequest)
            throws StudentProfileContentNotFoundException {
        return studentController.deactivateStudent(deactivateStudentRequest);
    }
    @PostMapping("/activateStudent")
    public ResponseEntity<Void> activateStudent(@RequestBody @Valid ActivateStudentRequest activateStudentRequest)
            throws StudentProfileContentNotFoundException {
        return studentController.activateStudent(activateStudentRequest);
    }




}
