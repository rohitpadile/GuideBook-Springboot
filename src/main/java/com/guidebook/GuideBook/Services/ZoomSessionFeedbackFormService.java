package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.ZoomSessionFeedbackForm;
import com.guidebook.GuideBook.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.Repository.ZoomSessionFeedbackFormRepository;
import com.guidebook.GuideBook.dtos.zoomsessionbook.GetSubmittionStatusForFeedbackFormResponse;
import com.guidebook.GuideBook.dtos.zoomsessionbook.SubmitZoomSessionFeedbackFormRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoomSessionFeedbackFormService {

    private ZoomSessionFeedbackFormRepository zoomSessionFeedbackFormRepository;
    private ZoomSessionTransactionService zoomSessionTransactionService;

    @Autowired
    public ZoomSessionFeedbackFormService(ZoomSessionFeedbackFormRepository zoomSessionFeedbackFormRepository,
                                          ZoomSessionTransactionService zoomSessionTransactionService) {
        this.zoomSessionFeedbackFormRepository = zoomSessionFeedbackFormRepository;
        this.zoomSessionTransactionService = zoomSessionTransactionService;
    }


    public void submitZoomSessionFeedbackForm(SubmitZoomSessionFeedbackFormRequest request) {
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

        transaction.setZoomSessionFeedbackFormId(savedForm.getZoomSessionFeedbackFormId());
        zoomSessionTransactionService.saveZoomSessionTransaction(transaction);
        //saved transaction with the feedback form id - that officially completes one session.

    }

    public GetSubmittionStatusForFeedbackFormResponse getSubmittionStatusForFeedbackForm(String transactionId) {

        ZoomSessionTransaction transaction = zoomSessionTransactionService.getZoomSessionTransactionById(transactionId);
        if(transaction.getZoomSessionFeedbackFormId() != null){
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
