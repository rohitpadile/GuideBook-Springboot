package com.guidebook.GuideBook.USER.scheduleServices;

import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import com.guidebook.GuideBook.USER.Repository.SubscriptionOrderRepository;
import com.guidebook.GuideBook.USER.Service.ClientAccountService;
import com.guidebook.GuideBook.USER.Service.StudentMentorAccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionCleanupService {


    private SubscriptionOrderRepository subscriptionOrderRepository;
    private StudentMentorAccountService studentMentorAccountService;
    private ClientAccountService clientAccountService;
    @Autowired
    public SubscriptionCleanupService(SubscriptionOrderRepository subscriptionOrderRepository,
                                      StudentMentorAccountService studentMentorAccountService,
                                      ClientAccountService clientAccountService) {
        this.subscriptionOrderRepository = subscriptionOrderRepository;
        this.studentMentorAccountService = studentMentorAccountService;
        this.clientAccountService = clientAccountService;
    }

    // Scheduled task to run every 24 hours
    @Scheduled(cron = "0 0 0 * * ?") // This cron expression schedules the task to run at midnight every day
    @Transactional
    public void deactivateExpiredSubscriptions() {
        // Get current time from the database
        Date now = subscriptionOrderRepository.getCurrentDatabaseTime();

        // Find all mentor accounts with expired subscriptions
        List<StudentMentorAccount> expiredMentorAccounts = studentMentorAccountService.findExpiredSubscriptions(now);
        for (StudentMentorAccount acc : expiredMentorAccounts) {
            acc.setStudentMentorAccountSubscription_Monthly(0); // Disable the subscription
            studentMentorAccountService.updateStudentMentorAccount(acc);
        }

        // Find all client accounts with expired subscriptions
        List<ClientAccount> expiredClientAccounts = clientAccountService.findExpiredSubscriptions(now);
        for (ClientAccount acc : expiredClientAccounts) {
            acc.setClientAccountSubscription_Monthly(0); // Disable the subscription
            clientAccountService.updateClientAccount(acc);
        }
    }
}