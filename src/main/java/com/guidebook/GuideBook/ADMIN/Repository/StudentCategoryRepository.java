package com.guidebook.GuideBook.ADMIN.Repository;

import com.guidebook.GuideBook.ADMIN.Models.StudentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCategoryRepository extends JpaRepository<StudentCategory, Long> {
    StudentCategory findStudentCategoryByStudentCategoryNameIgnoreCase(String name);
}
