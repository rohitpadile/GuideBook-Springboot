package com.guidebook.GuideBook.Services.regular;

import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StudentService {

    private StudentRepository studentRepository;
    @Autowired
    StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
