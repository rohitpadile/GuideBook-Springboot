package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByStudentMis(Long studentMis);
    Student findByStudentWorkEmail(String studentWorkEmail); //This is case sensitive
}