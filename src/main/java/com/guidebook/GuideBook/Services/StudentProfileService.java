package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.StudentProfile;
import com.guidebook.GuideBook.Repository.StudentProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentProfileService {

    private StudentProfileRepository studentProfileRepository;
    @Autowired
    public StudentProfileService(StudentProfileRepository studentProfileRepository) {
        this.studentProfileRepository = studentProfileRepository;
    }

    public StudentProfile addStudentProfile(StudentProfile studentProfile){
        return studentProfileRepository.save(studentProfile);
    }
}
