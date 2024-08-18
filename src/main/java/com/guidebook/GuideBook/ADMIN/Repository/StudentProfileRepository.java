package com.guidebook.GuideBook.ADMIN.Repository;

import com.guidebook.GuideBook.ADMIN.Models.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    StudentProfile findStudentProfileByStudentMis(Long mis);
    Optional<StudentProfile> findStudentProfileByStudentWorkEmailIgnoreCase(String workEmail); //Case Sensitive email

    // Native Query to get the current date and time from the database server
    @Query(value = "SELECT CURRENT_DATE()", nativeQuery = true)
    Date getCurrentDatabaseDate();
}