package com.guidebook.GuideBook.USER.Service;

import com.guidebook.GuideBook.ADMIN.Repository.StudentRepository;
import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import com.guidebook.GuideBook.USER.Repository.StudentMentorAccountRepository;
import com.guidebook.GuideBook.USER.dtos.EditClientAccountRequest;
import com.guidebook.GuideBook.USER.exceptions.StudentMentorAccountNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentMentorAccountService {
    private final StudentMentorAccountRepository studentMentorAccountRepository;
    private final StudentRepository studentRepository;
    @Autowired
    public StudentMentorAccountService(StudentMentorAccountRepository studentMentorAccountRepository,
                                       StudentRepository studentRepository) {
        this.studentMentorAccountRepository = studentMentorAccountRepository;
        this.studentRepository = studentRepository;
    }

    public StudentMentorAccount getAccountByEmail(String email){
        return studentMentorAccountRepository.findByStudentMentorAccountWorkEmail(email);
    }

    @Transactional
    public void editStudentMentorAccountDetails(EditClientAccountRequest editStudentMentorAccountRequest, String studentMentorEmail)
            throws StudentMentorAccountNotFoundException {
        StudentMentorAccount studentMentorAccount = studentMentorAccountRepository.findByStudentMentorAccountWorkEmail(studentMentorEmail);

        if(studentMentorAccount!=null){
            studentMentorAccount.setClientFirstName(editStudentMentorAccountRequest.getClientFirstName());
            studentMentorAccount.setClientMiddleName(editStudentMentorAccountRequest.getClientMiddleName());
            studentMentorAccount.setClientLastName(editStudentMentorAccountRequest.getClientLastName());
            studentMentorAccount.setClientPhoneNumber(editStudentMentorAccountRequest.getClientPhoneNumber());
            studentMentorAccount.setClientAge(editStudentMentorAccountRequest.getClientAge());
            studentMentorAccount.setClientCollege(
                    studentRepository.findByStudentWorkEmail(studentMentorEmail).getStudentCollege().getCollegeName()
            );
            studentMentorAccount.setClientZoomEmail(editStudentMentorAccountRequest.getClientZoomEmail());
            studentMentorAccount.setClientValidProof(editStudentMentorAccountRequest.getClientValidProof());
            //Set this manually here

            ////
            studentMentorAccountRepository.save(studentMentorAccount);
        } else {
            throw new StudentMentorAccountNotFoundException("student mentor account not found at editStudentMentorAccountDetails() method");
        }
    }
    public void addStudentMentorAccount(StudentMentorAccount studentMentorAccount){
        studentMentorAccountRepository.save(studentMentorAccount);
    }
}
