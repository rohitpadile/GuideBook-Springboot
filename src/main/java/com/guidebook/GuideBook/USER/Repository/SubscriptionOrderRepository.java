package com.guidebook.GuideBook.USER.Repository;

import com.guidebook.GuideBook.USER.Models.SubscriptionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface SubscriptionOrderRepository extends JpaRepository<SubscriptionOrder, String> {
    SubscriptionOrder findBySubscriptionRzpOrderId(String subscriptionRzpOrderId);
    @Query("SELECT CURRENT_TIMESTAMP")
    Date getCurrentDatabaseTime();
}
