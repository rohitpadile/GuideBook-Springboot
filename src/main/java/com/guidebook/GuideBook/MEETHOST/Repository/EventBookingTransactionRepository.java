package com.guidebook.GuideBook.MEETHOST.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.guidebook.GuideBook.MEETHOST.Model.EventBookingTransaction;
public interface EventBookingTransactionRepository extends JpaRepository<EventBookingTransaction, String> {
    EventBookingTransaction findByPaymentOrderRzpId(String paymentOrderRzpId);
}

