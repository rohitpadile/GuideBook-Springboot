package com.guidebook.GuideBook.TR.Services;

import com.guidebook.GuideBook.ADMIN.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TRStudentService {
    private StudentRepository studentRepository;
    @Autowired

    public TRStudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
}
