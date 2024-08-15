package com.guidebook.GuideBook.ADMIN.Services;

import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionForm;
import com.guidebook.GuideBook.ADMIN.exceptions.TransactionNotFoundException;
import com.guidebook.GuideBook.ADMIN.Models.Student;
import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.ADMIN.Repository.ZoomSessionTransactionRepository;
import com.guidebook.GuideBook.USER.Models.MyUser;
import com.guidebook.GuideBook.USER.Service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoomSessionTransactionService {
    private final MyUserService myUserService;
    private ZoomSessionTransactionRepository zoomSessionTransactionRepository;
    @Autowired
    public ZoomSessionTransactionService(ZoomSessionTransactionRepository zoomSessionTransactionRepository,
                                         MyUserService myUserService) {
        this.zoomSessionTransactionRepository = zoomSessionTransactionRepository;
        this.myUserService = myUserService;
    }
    //for free transaction
    public ZoomSessionTransaction createFreeTransaction(Student student, ZoomSessionForm form) {
        ZoomSessionTransaction zoomSessionTransaction = new ZoomSessionTransaction();
        zoomSessionTransaction.setZoomSessionForm(form);
        zoomSessionTransaction.setStudent(student);
        zoomSessionTransaction.setTransactionAmount(0.00);
        return zoomSessionTransactionRepository.save(zoomSessionTransaction);
    }

    public ZoomSessionTransaction createPaidTransaction(Student student, ZoomSessionForm form) {
        ZoomSessionTransaction zoomSessionTransaction = new ZoomSessionTransaction();
        zoomSessionTransaction.setZoomSessionForm(form);
        zoomSessionTransaction.setStudent(student);
//        zoomSessionTransaction.setTransactionAmount(myUserService.getIndividualZoomSessionAmount());
        zoomSessionTransaction.setTransactionStatus("created"); //created or paid
        zoomSessionTransaction.setUserEmail(form.getUserEmail());
        return zoomSessionTransactionRepository.save(zoomSessionTransaction);
    }

    public ZoomSessionTransaction getZoomSessionTransactionById(String zoomSessionTransactionId)
    throws TransactionNotFoundException {
        return zoomSessionTransactionRepository.findByZoomSessionTransactionId(zoomSessionTransactionId);
    }
    public ZoomSessionTransaction saveZoomSessionTransaction(ZoomSessionTransaction transaction){
        return zoomSessionTransactionRepository.save(transaction);
    }

    public List<ZoomSessionTransaction> getAllZoomSessionTransaction() {
        return zoomSessionTransactionRepository.findAll();
    }

}
