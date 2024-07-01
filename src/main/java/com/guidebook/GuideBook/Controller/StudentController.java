package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Services.StudentService;
import com.guidebook.GuideBook.dtos.AddStudentRequest;
import com.guidebook.GuideBook.dtos.FilteredStudentListRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/filteredStudentList")
    public ResponseEntity<List<Student>> getFilteredStudentList(@RequestBody @Valid FilteredStudentListRequest filteredStudentListRequest){
        List<Student> filteredStudentList = studentService.filteredStudentListRequest(filteredStudentListRequest);
        return new ResponseEntity<>(filteredStudentList, HttpStatus.OK);
    }

    public ResponseEntity<Student> addStudent(AddStudentRequest addStudentRequest){
        Student addedStudent = studentService.addStudent(addStudentRequest);
        return new ResponseEntity<>(addedStudent, HttpStatus.CREATED);
    }
}
