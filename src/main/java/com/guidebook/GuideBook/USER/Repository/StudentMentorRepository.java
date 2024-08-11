package com.guidebook.GuideBook.USER.Repository;

import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMentorRepository  extends JpaRepository<StudentMentorAccount, String> {
    StudentMentorAccount findByStudentMentorAccountWorkEmail(String email);
}
