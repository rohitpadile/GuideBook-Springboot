package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}