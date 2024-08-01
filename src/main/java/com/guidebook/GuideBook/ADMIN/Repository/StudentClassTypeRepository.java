package com.guidebook.GuideBook.ADMIN.Repository;

import com.guidebook.GuideBook.ADMIN.Models.StudentClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentClassTypeRepository extends JpaRepository<StudentClassType, Long> {
    StudentClassType findStudentClassTypeByStudentClassTypeName(String name);
}
