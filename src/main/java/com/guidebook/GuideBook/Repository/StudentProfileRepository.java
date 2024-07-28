package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.StudentClassType;
import com.guidebook.GuideBook.Models.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    StudentProfile findStudentProfileByStudentMis(Long mis);
    Optional<StudentProfile> findStudentProfileByStudentWorkEmail(String workEmail); //Case Sensitive email
}