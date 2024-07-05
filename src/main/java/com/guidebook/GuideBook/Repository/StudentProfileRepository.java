package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.StudentClassType;
import com.guidebook.GuideBook.Models.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    StudentProfile findStudentProfileByStudentMis(Long mis);
    StudentProfile findStudentProfileByStudentWorkEmail(String workEmail); //Case Sensitive email
}