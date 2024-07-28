package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.StudentProfile;
import com.guidebook.GuideBook.Models.ZoomSessionFeedbackForm;
import com.guidebook.GuideBook.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.Repository.ZoomSessionFeedbackFormRepository;
import com.guidebook.GuideBook.dtos.zoomsessionbook.GetSubmittionStatusForFeedbackFormResponse;
import com.guidebook.GuideBook.dtos.zoomsessionbook.SubmitZoomSessionFeedbackFormRequest;
import com.guidebook.GuideBook.exceptions.StudentProfileContentNotFoundException;
import com.guidebook.GuideBook.exceptions.TransactionNotFoundException;
import com.guidebook.GuideBook.mapper.StudentProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoomSessionFeedbackFormService {

    private ZoomSessionFeedbackFormRepository zoomSessionFeedbackFormRepository;
    private ZoomSessionTransactionService zoomSessionTransactionService;
    private StudentProfileService studentProfileService;

    @Autowired
    public ZoomSessionFeedbackFormService(ZoomSessionFeedbackFormRepository zoomSessionFeedbackFormRepository,
                                          ZoomSessionTransactionService zoomSessionTransactionService,
                                          StudentProfileService studentProfileService) {
        this.zoomSessionFeedbackFormRepository = zoomSessionFeedbackFormRepository;
        this.zoomSessionTransactionService = zoomSessionTransactionService;
        this.studentProfileService = studentProfileService;
    }

//THIS CAN ALSO BE A @Transactional
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

        //Increase the session count of student by 1
//        StudentProfile studentProfile = studentProfileService.getStudentProfileForFeedbackFormSuccess(transaction.getStudent().getStudentWorkEmail());
//        studentProfile.setStudentProfileSessionsConducted(studentProfile.getStudentProfileSessionsConducted() + 1);
//        studentProfileService.updateStudentProfile(studentProfile);

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
