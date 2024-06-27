package com.guidebook.GuideBook.Services.admin;



import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminStudentService {
    private StudentRepository studentRepository;

    @Autowired
    public AdminStudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student saveOrUpdateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }
}

