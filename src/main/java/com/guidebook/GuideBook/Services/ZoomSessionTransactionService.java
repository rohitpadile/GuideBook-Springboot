package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Models.ZoomSessionForm;
import com.guidebook.GuideBook.Models.ZoomSessionTransaction;
import com.guidebook.GuideBook.Repository.ZoomSessionTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ZoomSessionTransactionService {
//    @Value("transactionamount")
//    private Double transactionAmount;
    private ZoomSessionTransactionRepository zoomSessionTransactionRepository;
    @Autowired
    public ZoomSessionTransactionService(ZoomSessionTransactionRepository zoomSessionTransactionRepository) {
        this.zoomSessionTransactionRepository = zoomSessionTransactionRepository;
    }
    //for free transaction
    public ZoomSessionTransaction createFreeTransaction(Student student, ZoomSessionForm form) {
        ZoomSessionTransaction zoomSessionTransaction = new ZoomSessionTransaction();
        zoomSessionTransaction.setZoomSessionForm(form);
        zoomSessionTransaction.setStudent(student);
        zoomSessionTransaction.setTransactionAmount(0.00);
        return zoomSessionTransactionRepository.save(zoomSessionTransaction);
    }

    public ZoomSessionTransaction getZoomSessionTransactionById(String zoomSessionTransactionId){
        return zoomSessionTransactionRepository.findByZoomSessionTransactionId(zoomSessionTransactionId);
    }
    public ZoomSessionTransaction saveZoomSessionTransaction(ZoomSessionTransaction transaction){
        return zoomSessionTransactionRepository.save(transaction);
    }


}
