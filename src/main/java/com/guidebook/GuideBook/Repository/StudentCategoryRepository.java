package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.StudentCategory;
import com.guidebook.GuideBook.Models.StudentClassType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCategoryRepository extends JpaRepository<StudentCategory, Long> {
    StudentCategory findStudentCategoryByStudentCategoryNameIgnoreCase(String name);
}
