package com.guidebook.GuideBook.USER.scheduleServices;

import com.guidebook.GuideBook.USER.Repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class OtpCleanupScheduler {


    private OtpRepository otpRepository;
    @Autowired
    public OtpCleanupScheduler(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    @Scheduled(fixedRate = 60000) // Runs every minute
    public void deleteExpiredOtps() {
        // Fetch the current time from the database server
        Date currentTime = otpRepository.getCurrentDatabaseTime();

        // Calculate the expiration time
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTime);
        cal.add(Calendar.MINUTE, -5);
        Date expiryDate = cal.getTime();

        // Delete all OTPs older than 5 minutes based on database time
        otpRepository.deleteByCreatedOnBefore(expiryDate);
    }
}
