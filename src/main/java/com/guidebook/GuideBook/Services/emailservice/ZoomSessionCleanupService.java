package com.guidebook.GuideBook.Services.emailservice;

import com.guidebook.GuideBook.Repository.ZoomSessionFormRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Service
public class ZoomSessionCleanupService {

    @Autowired
    private ZoomSessionFormRepository zoomSessionFormRepository;

    @Scheduled(fixedRate = 600000) // Runs every 10 minutes
    public void cleanUpUnverifiedForms() {
        zoomSessionFormRepository.deleteByIsVerified(0);
    }
}
