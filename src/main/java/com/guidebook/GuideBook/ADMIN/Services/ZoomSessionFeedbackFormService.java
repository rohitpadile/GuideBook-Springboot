package com.guidebook.GuideBook.ADMIN.Services;

import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionForm;
import com.guidebook.GuideBook.ADMIN.Services.zoomsessionbook.ZoomSessionFormService;
import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionbook.GetSubmittionStatusForFeedbackFormResponse;
import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionbook.SubmitZoomSessionFeedbackFormRequest;
import com.guidebook.GuideBook.ADMIN.enums.ZoomSessionBookStatus;
import com.guidebook.GuideBook.ADMIN.exceptions.SessionAlreadyCancelledException;
import com.guidebook.GuideBook.ADMIN.exceptions.StudentProfileContentNotFoundException;
import com.guidebook.GuideBook.ADMIN.exceptions.TransactionNotFoundException;
import com.guidebook.GuideBook.ADMIN.Models.StudentProfile;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionFeedbackForm;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.ADMIN.Repository.ZoomSessionFeedbackFormRepository;
import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import com.guidebook.GuideBook.USER.Service.ClientAccountService;
import com.guidebook.GuideBook.USER.Service.MyUserService;
import com.guidebook.GuideBook.USER.Service.StudentMentorAccountService;
import com.guidebook.GuideBook.USER.exceptions.ClientAccountNotFoundException;
import com.guidebook.GuideBook.USER.exceptions.StudentMentorAccountNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ZoomSessionFeedbackFormService {
    private final ClientAccountService clientAccountService;
    private final StudentMentorAccountService studentMentorAccountService;

    private final ZoomSessionFeedbackFormRepository zoomSessionFeedbackFormRepository;
    private final ZoomSessionTransactionService zoomSessionTransactionService;
    private final StudentProfileService studentProfileService;
    private final MyUserService myUserService;

    @Autowired
    public ZoomSessionFeedbackFormService(ZoomSessionFeedbackFormRepository zoomSessionFeedbackFormRepository,
                                          ZoomSessionTransactionService zoomSessionTransactionService,
                                          StudentProfileService studentProfileService,
                                          ClientAccountService clientAccountService,
                                          StudentMentorAccountService studentMentorAccountService,
                                          MyUserService myUserService
                                          ) {
        this.zoomSessionFeedbackFormRepository = zoomSessionFeedbackFormRepository;
        this.zoomSessionTransactionService = zoomSessionTransactionService;
        this.studentProfileService = studentProfileService;
        this.clientAccountService = clientAccountService;
        this.studentMentorAccountService = studentMentorAccountService;
        this.myUserService = myUserService;
    }

//THIS CAN ALSO BE A @Transactional
    @Transactional
    public void submitZoomSessionFeedbackForm(SubmitZoomSessionFeedbackFormRequest request)
            throws StudentProfileContentNotFoundException,
            TransactionNotFoundException,
            ClientAccountNotFoundException,
            SessionAlreadyCancelledException {
        //Make a new feedback form and stores it uuid in the transaction unit

        ZoomSessionTransaction transaction = zoomSessionTransactionService
                .getZoomSessionTransactionById(
                        request.getZoomSessionTransactionId());
        //Check if session is cancelled already, restrict submitting feedback if so.
        if((transaction.getZoomSessionForm().getZoomSessionBookStatus()).equalsIgnoreCase(
                ZoomSessionBookStatus.CANCELLED.toString()
        )){
           throw new SessionAlreadyCancelledException("Session is already cancelled with zoom session form id " +
                   transaction.getZoomSessionForm().getZoomSessionFormId() + " , cannot give feedback");
        }

        ZoomSessionFeedbackForm feedbackForm = getZoomSessionFeedbackForm(request); //private method for this
        ZoomSessionFeedbackForm savedForm = zoomSessionFeedbackFormRepository.save(feedbackForm);
        transaction.setZoomSessionFeedbackForm(savedForm);

        //Increase the count of the Client by 1
        // (if another mentor has booked this session, increase his/her session count as a client)
        Integer accType = myUserService.checkUserEmailAccountTypeGeneralPurpose(transaction.getZoomSessionForm().getUserEmail());
        if(accType == 1){
            //Student mentor account
            StudentMentorAccount studentMentorAccount = studentMentorAccountService.getAccountByEmail(
                    transaction.getZoomSessionForm().getUserEmail());
            studentMentorAccount.setStudentMentorAccountZoomSessionCount(
                    studentMentorAccount.getStudentMentorAccountZoomSessionCount() + 1);
            studentMentorAccountService.updateStudentMentorAccount(studentMentorAccount);
        } else if(accType == 2){
            ClientAccount clientAccount = clientAccountService.getAccountByEmail(
                    transaction.getZoomSessionForm().getUserEmail());
            clientAccount.setClientAccountZoomSessionCount(
                    clientAccount.getClientAccountZoomSessionCount() + 1
            );
            clientAccountService.updateClientAccount(clientAccount);
        }else{
            throw new ClientAccountNotFoundException("student mentor as a client or client account not found at submitZoomSessionFeedbackForm() method");
        }
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
        //Directly check the transaction status is paid or not and set isSubmitted
        boolean paymentDone = transaction.getTransactionStatus().equalsIgnoreCase("paid");
        if((paymentDone) && (transaction.getZoomSessionFeedbackForm()==null)){
            log.info("Session is already paid and booked");
            return GetSubmittionStatusForFeedbackFormResponse.builder()
                    .isSubmitted(0) //let it submit form
                    .build();
        } else{
            return GetSubmittionStatusForFeedbackFormResponse.builder()
                    .isSubmitted(1) //Cannot submit now
                    .build();
        }
    }
}
