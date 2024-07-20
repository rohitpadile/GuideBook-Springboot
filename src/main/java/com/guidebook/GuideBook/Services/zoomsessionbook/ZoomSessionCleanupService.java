package com.guidebook.GuideBook.Services.zoomsessionbook;

import com.guidebook.GuideBook.Repository.ZoomSessionFormRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
@Slf4j
@Service
public class ZoomSessionCleanupService {

    @Autowired
    private ZoomSessionFormRepository zoomSessionFormRepository;

    @Scheduled(fixedRate = 600000) // Runs every 10 minutes
    @Transactional
    public void cleanUpUnverifiedForms() {
        long count = zoomSessionFormRepository.countByIsVerified(0);
        zoomSessionFormRepository.deleteByIsVerified(0);
        log.info("Deleted unverified forms: {}", count);

    }
}
