package com.guidebook.GuideBook.ADMIN.Services;

import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionForm;
import com.guidebook.GuideBook.ADMIN.Services.zoomsessionbook.ZoomSessionFormService;
import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionbook.GetSubmittionStatusForFeedbackFormResponse;
import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionbook.SubmitZoomSessionFeedbackFormRequest;
import com.guidebook.GuideBook.ADMIN.exceptions.StudentProfileContentNotFoundException;
import com.guidebook.GuideBook.ADMIN.exceptions.TransactionNotFoundException;
import com.guidebook.GuideBook.ADMIN.Models.StudentProfile;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionFeedbackForm;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.ADMIN.Repository.ZoomSessionFeedbackFormRepository;
import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import com.guidebook.GuideBook.USER.Service.ClientAccountService;
import com.guidebook.GuideBook.USER.Service.StudentMentorAccountService;
import com.guidebook.GuideBook.USER.exceptions.ClientAccountNotFoundException;
import com.guidebook.GuideBook.USER.exceptions.StudentMentorAccountNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoomSessionFeedbackFormService {
    private final ClientAccountService clientAccountService;
    private final StudentMentorAccountService studentMentorAccountService;

    private final ZoomSessionFeedbackFormRepository zoomSessionFeedbackFormRepository;
    private final ZoomSessionTransactionService zoomSessionTransactionService;
    private final StudentProfileService studentProfileService;

    @Autowired
    public ZoomSessionFeedbackFormService(ZoomSessionFeedbackFormRepository zoomSessionFeedbackFormRepository,
                                          ZoomSessionTransactionService zoomSessionTransactionService,
                                          StudentProfileService studentProfileService,
                                          ClientAccountService clientAccountService,
                                          StudentMentorAccountService studentMentorAccountService
                                          ) {
        this.zoomSessionFeedbackFormRepository = zoomSessionFeedbackFormRepository;
        this.zoomSessionTransactionService = zoomSessionTransactionService;
        this.studentProfileService = studentProfileService;
        this.clientAccountService = clientAccountService;
        this.studentMentorAccountService = studentMentorAccountService;
    }

//THIS CAN ALSO BE A @Transactional
    @Transactional
    public void submitZoomSessionFeedbackForm(SubmitZoomSessionFeedbackFormRequest request)
            throws StudentProfileContentNotFoundException,
            TransactionNotFoundException,
            ClientAccountNotFoundException {
        //Make a new feedback form and stores it uuid in the transaction unit

        ZoomSessionFeedbackForm feedbackForm = getZoomSessionFeedbackForm(request); //private method for this
        ZoomSessionFeedbackForm savedForm = zoomSessionFeedbackFormRepository.save(feedbackForm);

        ZoomSessionTransaction transaction = zoomSessionTransactionService
                .getZoomSessionTransactionById(
                        request.getZoomSessionTransactionId());

        transaction.setZoomSessionFeedbackForm(savedForm);

//        Increase the session count of student by 1
        StudentProfile studentProfile = studentProfileService.getStudentProfileForGeneralPurpose(transaction.getStudent().getStudentWorkEmail());
        studentProfile.setStudentProfileSessionsConducted(studentProfile.getStudentProfileSessionsConducted() + 1);
        //Increase the count of the Client by 1 (if another mentor has booked this session, increase his/her session count)
        StudentMentorAccount studentMentorAccount = studentMentorAccountService.getAccountByEmail(
                transaction.getZoomSessionForm().getUserEmail());
        ClientAccount clientAccount = clientAccountService.getAccountByEmail(
                transaction.getZoomSessionForm().getUserEmail());
        if(studentMentorAccount!=null){
            studentMentorAccount.setStudentMentorAccountZoomSessionCount(
                    studentMentorAccount.getStudentMentorAccountZoomSessionCount() + 1
            );
        } else if(clientAccount!=null){
            clientAccount.setClientAccountZoomSessionCount(
                    clientAccount.getClientAccountZoomSessionCount() + 1
            );
            //NOTE: IF SAME EMAIL PRESENT IN BOTH, FIRST MENTOR ACCOUNT IS CHECKED AND PROCEEDED
            //IF MENTOR IS NOT PRESENT, THEN CLIENT ACCOUNT IS CHECKED AND PROCEEDED
            //IT WONT HAPPEN THAT BOTH ACCOUNTS SESSIONS ARE INCREMENTED
        }else{
            throw new ClientAccountNotFoundException("student mentor as a client or client account not found at submitZoomSessionFeedbackForm() method");
        }
        studentProfileService.updateStudentProfile(studentProfile);
        zoomSessionTransactionService.saveZoomSessionTransaction(transaction);

        //saved transaction with the feedback form id - that officially completes one session.

    }

    private static ZoomSessionFeedbackForm getZoomSessionFeedbackForm(SubmitZoomSessionFeedbackFormRequest request) {
        ZoomSessionFeedbackForm feedbackForm = new ZoomSessionFeedbackForm();
        feedbackForm.setOverallFeedback(request.getOverallFeedback().toUpperCase());
        feedbackForm.setPurposeFulfilled(request.getPurposeFulfilled());
        feedbackForm.setMoreFeedbackAboutStudent(request.getMoreFeedbackAboutStudent());
        feedbackForm.setFeedbackForCompany(request.getFeedbackForCompany());
        feedbackForm.setIsSubmitted(1);
        return feedbackForm;
    }

    public GetSubmittionStatusForFeedbackFormResponse getSubmittionStatusForFeedbackForm(String transactionId)
            throws TransactionNotFoundException
    {

        ZoomSessionTransaction transaction = zoomSessionTransactionService.getZoomSessionTransactionById(transactionId);
        if(transaction.getZoomSessionFeedbackForm() != null){
            return GetSubmittionStatusForFeedbackFormResponse.builder()
                    .isSubmitted(1)
                    .build();
        } else{
            return GetSubmittionStatusForFeedbackFormResponse.builder()
                    .isSubmitted(0)
                    .build();
        }
    }
}
