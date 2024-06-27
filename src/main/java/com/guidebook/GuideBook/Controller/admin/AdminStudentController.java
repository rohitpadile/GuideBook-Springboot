package com.guidebook.GuideBook.Controller.admin;

import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Services.admin.AdminStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/")
public class AdminStudentController {

    private AdminStudentService adminStudentService;
    @Autowired
    AdminStudentController(AdminStudentService adminStudentService){
        this.adminStudentService = adminStudentService;
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return adminStudentService.getAllStudents();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = adminStudentService.getStudentById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/addStudent")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student savedStudent = adminStudentService.saveOrUpdateStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @PutMapping("/addStudent/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        if (!adminStudentService.getStudentById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        student.setStudentId(id);
        Student updatedStudent = adminStudentService.saveOrUpdateStudent(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("deleteStudent/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!adminStudentService.getStudentById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        adminStudentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }
}
