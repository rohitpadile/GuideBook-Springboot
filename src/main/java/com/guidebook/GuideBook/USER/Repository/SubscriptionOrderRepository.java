package com.guidebook.GuideBook.USER.Repository;

import com.guidebook.GuideBook.USER.Models.SubscriptionOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionOrderRepository extends JpaRepository<SubscriptionOrder, String> {
    SubscriptionOrder findBySubscriptionRzpOrderId(String subscriptionRzpOrderId);

}
