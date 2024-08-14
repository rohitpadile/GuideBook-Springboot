package com.guidebook.GuideBook.USER.Repository;

import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StudentMentorAccountRepository extends JpaRepository<StudentMentorAccount, String> {
    StudentMentorAccount findByStudentMentorAccountWorkEmail(String email);
    List<StudentMentorAccount> findBySubscriptionEndDateBefore(Date date);
}
