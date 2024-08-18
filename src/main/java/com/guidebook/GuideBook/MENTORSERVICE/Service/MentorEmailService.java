package com.guidebook.GuideBook.MENTORSERVICE.Service;

import com.guidebook.GuideBook.ADMIN.Models.Student;
import com.guidebook.GuideBook.ADMIN.Models.StudentProfile;
import com.guidebook.GuideBook.ADMIN.Services.StudentProfileService;
import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.ADMIN.Services.emailservice.EmailServiceImpl;
import com.guidebook.GuideBook.ADMIN.exceptions.StudentProfileContentNotFoundException;
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
        
                We would like to inform you that we are now preferring Gmail as the standard work email for our communications. This change is due to ongoing issues with emails being sent to Outlook or other email services for booking sessions.
        
                To ensure smooth communication and booking process, please fill out the application form using a Gmail account dedicated to GuidebookX.
        
                We appreciate your cooperation in this matter and thank you for your continued support!
        
                Best regards,
                GuidebookX Team
                """;




        for (Student student : students) {
            String email = student.getStudentWorkEmail();
            emailServiceImpl.sendSimpleMessage(email, "Regarding work email - important notice", emailBody);
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
