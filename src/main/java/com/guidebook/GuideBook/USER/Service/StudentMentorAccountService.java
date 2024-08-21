package com.guidebook.GuideBook.USER.Service;

import com.guidebook.GuideBook.ADMIN.Models.StudentProfile;
import com.guidebook.GuideBook.ADMIN.Repository.StudentRepository;
import com.guidebook.GuideBook.ADMIN.Services.StudentProfileService;
import com.guidebook.GuideBook.ADMIN.Services.StudentService;
import com.guidebook.GuideBook.ADMIN.exceptions.StudentProfileContentNotFoundException;
import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import com.guidebook.GuideBook.USER.Repository.StudentMentorAccountRepository;
import com.guidebook.GuideBook.USER.dtos.EditMentorAccountRequest;
import com.guidebook.GuideBook.USER.exceptions.StudentMentorAccountNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class StudentMentorAccountService {
    private final StudentMentorAccountRepository studentMentorAccountRepository;
    private final StudentRepository studentRepository;
    private final StudentProfileService studentProfileService;
    @Autowired
    public StudentMentorAccountService(StudentMentorAccountRepository studentMentorAccountRepository,
                                       StudentRepository studentRepository,
                                       StudentProfileService studentProfileService) {
        this.studentMentorAccountRepository = studentMentorAccountRepository;
        this.studentRepository = studentRepository;
        this.studentProfileService = studentProfileService;
    }

    public StudentMentorAccount getAccountByEmail(String email){
        return studentMentorAccountRepository.findByStudentMentorAccountWorkEmail(email);
    }

    @Transactional
    public void editStudentMentorAccountDetails(EditMentorAccountRequest editStudentMentorAccountRequest, String studentMentorEmail)
            throws StudentMentorAccountNotFoundException,
            StudentProfileContentNotFoundException {
        StudentMentorAccount studentMentorAccount = studentMentorAccountRepository.findByStudentMentorAccountWorkEmail(studentMentorEmail);
        StudentProfile profile = studentProfileService.getStudentProfileForGeneralPurpose(studentMentorEmail);
        if(studentMentorAccount!=null){
            studentMentorAccount.setClientFirstName(editStudentMentorAccountRequest.getClientFirstName());
            studentMentorAccount.setClientMiddleName(editStudentMentorAccountRequest.getClientMiddleName());
            studentMentorAccount.setClientLastName(editStudentMentorAccountRequest.getClientLastName());
            studentMentorAccount.setClientPhoneNumber(editStudentMentorAccountRequest.getClientPhoneNumber());
            studentMentorAccount.setClientAge(editStudentMentorAccountRequest.getClientAge());
            studentMentorAccount.setClientCollege(

                    ///////IMPORTANT LESSON HERE.//////////
                    //Lesson: Use mapping instead of autowiring service classes
                    //because circular reference is giving errors.
                    //That's why here I am using studentRepository for now.
                    //Don't use studentService here, it will create errors
                    studentRepository.findByStudentWorkEmail(studentMentorEmail).getStudentCollege().getCollegeName()
            );
            studentMentorAccount.setClientZoomEmail(editStudentMentorAccountRequest.getClientZoomEmail());
            studentMentorAccount.setClientValidProof(editStudentMentorAccountRequest.getClientValidProof());
            //Set this manually here
            //update sessions per week and remaining per week
            log.info("I am near updating session per week but i removed them from frontend ");
            profile.setZoomSessionsPerWeek(editStudentMentorAccountRequest.getZoomSessionsPerWeek());
            profile.setZoomSessionsRemainingPerWeek(editStudentMentorAccountRequest.getZoomSessionsRemainingPerWeek());
            ////
            studentMentorAccountRepository.save(studentMentorAccount);
        } else {
            throw new StudentMentorAccountNotFoundException("student mentor account not found at editStudentMentorAccountDetails() method");
        }
    }
    public void addStudentMentorAccount(StudentMentorAccount studentMentorAccount){
        studentMentorAccountRepository.save(studentMentorAccount);
    }

    public void updateStudentMentorAccount(StudentMentorAccount studentMentorAccount){
        studentMentorAccountRepository.save(studentMentorAccount);
    }

    @Transactional
    public List<StudentMentorAccount> findExpiredSubscriptions(Date now) {
        return studentMentorAccountRepository.findBySubscriptionEndDateBefore(now);
    }

    public List<StudentMentorAccount> getAllStudentMentorAccounts(){
        return studentMentorAccountRepository.findAll();
    }
}
