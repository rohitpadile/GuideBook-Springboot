package com.guidebook.GuideBook.Services;



import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Repository.StudentRepository;
import com.guidebook.GuideBook.Repository.cutomrepository.CustomStudentRepositoryImpl;
import com.guidebook.GuideBook.dtos.FilteredStudentListRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Filter;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    private CustomStudentRepositoryImpl customStudentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CustomStudentRepositoryImpl customStudentRepository) {
        this.studentRepository = studentRepository;
        this.customStudentRepository = customStudentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> filteredStudentListRequest(FilteredStudentListRequest filteredStudentListRequest){
        return customStudentRepository.findStudentsByFilters(filteredStudentListRequest);
    }

}

