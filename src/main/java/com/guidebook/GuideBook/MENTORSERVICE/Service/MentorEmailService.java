package com.guidebook.GuideBook.MENTORSERVICE.Service;

import com.guidebook.GuideBook.ADMIN.Models.Student;
import com.guidebook.GuideBook.ADMIN.Models.StudentProfile;
import com.guidebook.GuideBook.ADMIN.Services.StudentProfileService;
import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.ADMIN.Services.emailservice.EmailServiceImpl;
import com.guidebook.GuideBook.ADMIN.exceptions.StudentProfileContentNotFoundException;
import com.guidebook.GuideBook.MENTORSERVICE.dtos.SendMailToAllMentorsRequestMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentorEmailService {
    private final EmailServiceImpl emailServiceImpl;
    private final StudentService studentService;
    private final StudentProfileService studentProfileService;

    @Autowired
    public MentorEmailService(EmailServiceImpl  emailServiceImpl,
                              StudentService studentService,
                              StudentProfileService studentProfileService) {
        this.emailServiceImpl = emailServiceImpl;
        this.studentService = studentService;
        this.studentProfileService = studentProfileService;
    }

    public void sendEmailToAllMentors() {
        List<Student> students = studentService.getAllStudents();
        String emailBody =
                """
                Dear Student Mentors,
        
                We hope this message finds you well.
                
                Please fill out the names in the spreadsheet those who have trouble signing up on the website.
                We will try to sign you up from our side and give you a sample password which you need to
                do change by doing forgot password.
        
                https://docs.google.com/spreadsheets/d/1QWg909PoQiZ_FhfShZRvJSOexiSpaR0VwSE9SNYKqGM/edit?usp=sharing
        
                We appreciate your cooperation in this matter and thank you for your continued support!
        
                Best regards,
                GuidebookX Team
                """;




        for (Student student : students) {
            String email = student.getStudentWorkEmail();
            emailServiceImpl.sendSimpleMessage(email, "Regarding work email - important notice", emailBody);
        }
    }

    public void sendEmailToAllMentors(SendMailToAllMentorsRequestMail requestMail) {
        List<Student> students = studentService.getAllStudents();
        for (Student student : students) {
            String email = student.getStudentWorkEmail();
            emailServiceImpl.sendSimpleMessage(email, requestMail.getMailSubject(), requestMail.getMailContent());
        }
    }

    public void setAllMentorSessionsPerWeekToZero()
            throws StudentProfileContentNotFoundException {
        List<StudentProfile> list = studentProfileService.getAllStudentProfiles();
        for(StudentProfile profile : list){
            profile.setZoomSessionsPerWeek(7);
            profile.setZoomSessionsRemainingPerWeek(7);
            studentProfileService.updateStudentProfile(profile);
        }
    }
}
