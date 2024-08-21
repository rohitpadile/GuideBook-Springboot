package com.guidebook.GuideBook.ADMIN.Repository;

import com.guidebook.GuideBook.ADMIN.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByStudentWorkEmailIgnoreCase(String studentWorkEmail); //This is case sensitive
    Student findByStudentWorkEmail(String studentWorkEmail);

//    List<Student> findByStudentNameIgnoreCase(String studentName);
}