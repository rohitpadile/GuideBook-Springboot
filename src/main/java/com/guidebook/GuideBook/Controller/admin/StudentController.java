package com.guidebook.GuideBook.Controller.admin;

import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Services.admin.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/")
public class StudentController {

    private StudentService studentService;
    @Autowired
    StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> studentList = studentService.getAllStudents();
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/addStudent")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student savedStudent = studentService.addStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @PutMapping("/updateStudent/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudentById(id, student);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @DeleteMapping("deleteStudent/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("deleteAllStudents")
    public ResponseEntity<Void> deleteAllStudents(){
        studentService.deleteAllStudents();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //make dto's for studentProfile if possible
}
