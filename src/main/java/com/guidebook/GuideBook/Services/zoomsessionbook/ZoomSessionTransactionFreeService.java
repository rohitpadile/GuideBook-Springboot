package com.guidebook.GuideBook.Services.zoomsessionbook;

import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Models.ZoomSessionForm;
import com.guidebook.GuideBook.Models.ZoomSessionTransactionFree;
import com.guidebook.GuideBook.Repository.ZoomSessionTransactionFreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoomSessionTransactionFreeService {

    private ZoomSessionTransactionFreeRepository zoomSessionTransactionFreeRepository;
    @Autowired
    public ZoomSessionTransactionFreeService(ZoomSessionTransactionFreeRepository zoomSessionTransactionFreeRepository) {
        this.zoomSessionTransactionFreeRepository = zoomSessionTransactionFreeRepository;
    }

    public ZoomSessionTransactionFree createTransactionFree(Student student, ZoomSessionForm form) {
        return ZoomSessionTransactionFree.builder()
                    .zoomSessionForm(form)
                    .student(student)
                    .transactionAmount(0.00)
                    .build();
    }
}
