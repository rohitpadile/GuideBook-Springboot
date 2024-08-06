package com.guidebook.GuideBook.MENTORSERVICE.Service;

import com.guidebook.GuideBook.ADMIN.Models.Student;
import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.ADMIN.Services.emailservice.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentorEmailService {
    private final EmailServiceImpl emailServiceImpl;
    private final StudentService studentService;

    @Autowired
    public MentorEmailService(EmailServiceImpl  emailServiceImpl, StudentService studentService) {
        this.emailServiceImpl = emailServiceImpl;
        this.studentService = studentService;
    }

    public void sendEmailToAllMentors() {
        List<Student> students = studentService.getAllStudents();
        String emailBody =
                "Dear Student Mentors,\n\n" +
                "We hope this message finds you well.\n\n" +
                "We are thrilled to announce that GuidebookX is expanding to include students from other colleges! This means more opportunities to connect with peers across different institutions, share your valuable insights, and help even more students succeed.\n\n" +
                "In addition, we are excited to inform you that school students are eagerly waiting to book sessions with you. They are looking forward to receiving guidance on college admissions, entrance exams, study tips, and much more.\n\n" +
                "We appreciate your continued support and commitment to making GuidebookX a success.\n\n" +
                "Thank you for being a part of this journey with us. Let's continue to make a difference together!\n\n" +
                "Best regards,\n" +
                "GuidebookX Team";


        for (Student student : students) {
            String email = student.getStudentWorkEmail();
            emailServiceImpl.sendSimpleMessage(email, "Exciting News: GuidebookX is Expanding!", emailBody);
        }
    }
}
