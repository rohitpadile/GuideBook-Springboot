package com.guidebook.GuideBook.USER.Service;

import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import com.guidebook.GuideBook.USER.Repository.StudentMentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentMentorService {
    private final StudentMentorRepository studentMentorRepository;
    @Autowired
    public StudentMentorService(StudentMentorRepository studentMentorRepository) {
        this.studentMentorRepository = studentMentorRepository;
    }

    public StudentMentorAccount getAccountByEmail(String email){
        return studentMentorRepository.findByStudentMentorAccountWorkEmail(email);
    }
}
