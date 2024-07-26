package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.BookingRestriction;
import com.guidebook.GuideBook.Repository.BookingRestrictionRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service

public class BookingRestrictionService {

    @Autowired
    private BookingRestrictionRepository bookingRestrictionRepository;

    @Value("${timeBeforeBookSessionNotAllowed}")
    @Getter
    private int timeBeforeBookSessionNotAllowed; // Time in hours

    // Run this task every hour
    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void deleteExpiredEntries() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -timeBeforeBookSessionNotAllowed); // Use the injected value
        Date expiryTime = calendar.getTime();
        bookingRestrictionRepository.deleteByCreatedOnBefore(expiryTime);
    }

    public Optional<BookingRestriction> findByClientEmail(String clientEmail) {
        return bookingRestrictionRepository.findByClientEmail(clientEmail);
    }

    public void save(BookingRestriction restriction) {
        bookingRestrictionRepository.save(restriction);
    }
}