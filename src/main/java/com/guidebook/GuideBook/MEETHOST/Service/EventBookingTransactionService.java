package com.guidebook.GuideBook.MEETHOST.Service;

import com.guidebook.GuideBook.MEETHOST.Model.EventBookingTransaction;
import com.guidebook.GuideBook.MEETHOST.Repository.EventBookingTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventBookingTransactionService {
    private final EventBookingTransactionRepository eventBookingTransactionRepository;
    @Autowired
    public EventBookingTransactionService(EventBookingTransactionRepository eventBookingTransactionRepository) {
        this.eventBookingTransactionRepository = eventBookingTransactionRepository;
    }

    public void saveTransaction(EventBookingTransaction transaction){
        eventBookingTransactionRepository.save(transaction);
    }
    public EventBookingTransaction getByPaymentOrderRzpId(String paymentOrderRzpId){
        return eventBookingTransactionRepository.findByPaymentOrderRzpId(paymentOrderRzpId);
    }
}
