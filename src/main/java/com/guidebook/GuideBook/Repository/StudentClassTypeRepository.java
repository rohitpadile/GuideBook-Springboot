package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.StudentClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentClassTypeRepository extends JpaRepository<StudentClassType, Long> {
    StudentClassType findStudentClassTypeByStudentClassTypeName(String name);
}
