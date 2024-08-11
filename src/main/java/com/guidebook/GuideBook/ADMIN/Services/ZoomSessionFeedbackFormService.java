package com.guidebook.GuideBook.ADMIN.Services;

import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionbook.GetSubmittionStatusForFeedbackFormResponse;
import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionbook.SubmitZoomSessionFeedbackFormRequest;
import com.guidebook.GuideBook.ADMIN.exceptions.StudentProfileContentNotFoundException;
import com.guidebook.GuideBook.ADMIN.exceptions.TransactionNotFoundException;
import com.guidebook.GuideBook.ADMIN.Models.StudentProfile;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionFeedbackForm;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.ADMIN.Repository.ZoomSessionFeedbackFormRepository;
import com.guidebook.GuideBook.USER.Service.ClientAccountService;
import com.guidebook.GuideBook.USER.Service.StudentMentorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoomSessionFeedbackFormService {
    private final ClientAccountService clientAccountService;
    private final StudentMentorService studentMentorService;

    private final ZoomSessionFeedbackFormRepository zoomSessionFeedbackFormRepository;
    private final ZoomSessionTransactionService zoomSessionTransactionService;
    private final StudentProfileService studentProfileService;

    @Autowired
    public ZoomSessionFeedbackFormService(ZoomSessionFeedbackFormRepository zoomSessionFeedbackFormRepository,
                                          ZoomSessionTransactionService zoomSessionTransactionService,
                                          StudentProfileService studentProfileService,
                                          ClientAccountService clientAccountService,
                                          StudentMentorService studentMentorService
                                          ) {
        this.zoomSessionFeedbackFormRepository = zoomSessionFeedbackFormRepository;
        this.zoomSessionTransactionService = zoomSessionTransactionService;
        this.studentProfileService = studentProfileService;
        this.clientAccountService = clientAccountService;
        this.studentMentorService = studentMentorService;
    }

//THIS CAN ALSO BE A @Transactional
    @Transactional
    public void submitZoomSessionFeedbackForm(SubmitZoomSessionFeedbackFormRequest request)
            throws StudentProfileContentNotFoundException, TransactionNotFoundException {
        //Make a new feedback form and stores it uuid in the transaction unit

        ZoomSessionFeedbackForm feedbackForm = new ZoomSessionFeedbackForm();
        feedbackForm.setOverallFeedback(request.getOverallFeedback().toUpperCase());
        feedbackForm.setPurposeFulfilled(request.getPurposeFulfilled());
        feedbackForm.setMoreFeedbackAboutStudent(request.getMoreFeedbackAboutStudent());
        feedbackForm.setFeedbackForCompany(request.getFeedbackForCompany());
        feedbackForm.setIsSubmitted(1);

        ZoomSessionFeedbackForm savedForm = zoomSessionFeedbackFormRepository.save(feedbackForm);

        ZoomSessionTransaction transaction = zoomSessionTransactionService
                .getZoomSessionTransactionById(
                        request.getZoomSessionTransactionId());

        transaction.setZoomSessionFeedbackForm(savedForm);

//        Increase the session count of student by 1
        StudentProfile studentProfile = studentProfileService.getStudentProfileForGeneralPurpose(transaction.getStudent().getStudentWorkEmail());
        studentProfile.setStudentProfileSessionsConducted(studentProfile.getStudentProfileSessionsConducted() + 1);
        //Increase the count of the Client by 1 (if another mentor has booked this session, increase this session count)

        studentProfileService.updateStudentProfile(studentProfile);

        zoomSessionTransactionService.saveZoomSessionTransaction(transaction);

        //saved transaction with the feedback form id - that officially completes one session.

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
