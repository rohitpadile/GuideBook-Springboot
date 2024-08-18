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
                """
                        Dear Student Mentors,

                        We hope this message finds you well.

                        We are pleased to inform you that going forward, your earnings will be based on the number of sessions you conduct. You now have complete flexibility to manage your availability.

                        To manage your public profiles and session availability, please log into your account using your GuidebookX work email. Once logged in, you can edit your public profile and set a limit on the number of sessions you wish to conduct per week.

                        We appreciate your continued support and look forward to seeing you help more students achieve success.

                        Thank you for being a part of the GuidebookX team!

                        Best regards,
                        GuidebookX Team""";



        for (Student student : students) {
            String email = student.getStudentWorkEmail();
            emailServiceImpl.sendSimpleMessage(email, "Important Notice to Mentors", emailBody);
        }
    }
}
