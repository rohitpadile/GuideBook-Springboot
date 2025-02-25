package com.guidebook.GuideBook.ADMIN.Controller;

import com.guidebook.GuideBook.ADMIN.dtos.*;
import com.guidebook.GuideBook.ADMIN.dtos.filterstudents.FilteredStudentListRequest;
import com.guidebook.GuideBook.ADMIN.exceptions.*;
import com.guidebook.GuideBook.ADMIN.Models.Student;
import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.ADMIN.dtos.filterstudents.FilteredStudentDetails;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/admin/")
public class StudentController {

    private StudentService studentService;
    @Autowired
    StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/students") //kept this just for history reference
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> studentList = studentService.getAllStudents();
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @PostMapping("/filteredStudentList")
    public ResponseEntity<List<FilteredStudentDetails>> getFilteredStudentList(
//            https://chatgpt.com/c/cb198f94-40aa-4d4c-84dd-b48a78968fb9 -
//            use for pagination when required in the future
            @RequestBody @Valid FilteredStudentListRequest filteredStudentListRequest)
            throws FilteredStudentListNotFoundException
    {
        List<FilteredStudentDetails> response = studentService.getFilteredStudentList(filteredStudentListRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/addStudent")
    public ResponseEntity<GetStudentBasicDetailsResponse> addStudent(@RequestBody @Valid AddStudentRequest addStudentRequest)
            throws CollegeNotFoundException,
            BranchNotFoundException,
            StudentClassTypeNotFoundException,
            StudentCategoryNotFoundException,
            LanguageNotFoundException,
            StudentProfileContentNotFoundException,
            EncryptionFailedException,
            AlreadyPresentException {
        GetStudentBasicDetailsResponse response = studentService.addStudent(addStudentRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/studentBasicDetails/{workemail}")
    public ResponseEntity<GetStudentBasicDetailsResponse> getStudentBasicDetails(@PathVariable String workemail)
    throws StudentBasicDetailsNotFoundException
    {
        GetStudentBasicDetailsResponse res = studentService.getStudentBasicDetails(workemail);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

//    @GetMapping("/getStudentsWithName/{studentName}")
//    public ResponseEntity<List<GetStudentBasicDetailsResponse>> getStudentsWithName(@PathVariable String studentName){
//        List<GetStudentBasicDetailsResponse> res = studentService.getStudentsWithName(studentName);
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }

    @PostMapping("/updateStudent")
    public ResponseEntity<GetStudentBasicDetailsResponse> updateStudent(@RequestBody @Valid UpdateStudentRequest updateStudentRequest)
            throws StudentClassTypeNotFoundException,
            CollegeNotFoundException,
            StudentCategoryNotFoundException,
            LanguageNotFoundException,
            StudentNotFoundException {
        GetStudentBasicDetailsResponse response = studentService.updateStudent(updateStudentRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/deactivateStudent")
    public ResponseEntity<Void> deactivateStudent(@RequestBody @Valid DeactivateStudentRequest deactivateStudentRequest)
            throws StudentProfileContentNotFoundException,
            StudentNotFoundException {
        studentService.deactivateStudent(deactivateStudentRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/activateStudent")
    public ResponseEntity<Void> activateStudent(@RequestBody @Valid ActivateStudentRequest activateStudentRequest)
            throws StudentNotFoundException {
        studentService.activateStudent(activateStudentRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
