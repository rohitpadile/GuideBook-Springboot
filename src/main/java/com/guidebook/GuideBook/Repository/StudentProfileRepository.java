package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
}