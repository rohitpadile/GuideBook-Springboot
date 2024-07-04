package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Services.StudentService;
import com.guidebook.GuideBook.dtos.*;
import com.guidebook.GuideBook.exceptions.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000/", "http://localhost:8080"})
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
    public ResponseEntity<List<FilteredStudentDetails>> getFilteredStudentList(@RequestBody @Valid FilteredStudentListRequest filteredStudentListRequest){
        List<FilteredStudentDetails> response = studentService.getFilteredStudentList(filteredStudentListRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/addStudent")
    public ResponseEntity<Student> addStudent(@RequestBody @Valid AddStudentRequest addStudentRequest)
            throws CollegeNotFoundException,
            BranchNotFoundException,
            StudentClassTypeNotFoundException,
            StudentCategoryNotFoundException
    {
        Student addedStudent = studentService.addStudent(addStudentRequest);
        return new ResponseEntity<>(addedStudent, HttpStatus.CREATED);
    }

    @GetMapping("/studentBasicDetails/{mis}")
    public ResponseEntity<GetStudentBasicDetailsResponse> getStudentBasicDetails(@PathVariable Long mis)
    throws StudentBasicDetailsNotFoundException
    {
        GetStudentBasicDetailsResponse res = studentService.getStudentBasicDetails(mis);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
