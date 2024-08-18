package com.guidebook.GuideBook.ADMIN.Services;

import com.guidebook.GuideBook.ADMIN.Repository.BookingRestrictionRepository;
import com.guidebook.GuideBook.ADMIN.Models.BookingRestriction;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
    @Scheduled(fixedRate = 1800000) //Change this to 3600000 later //using it for production use now
    @Transactional
    public void deleteExpiredEntries() {
        // Fetch the current time from the database server
        Date currentTime = bookingRestrictionRepository.getCurrentDatabaseTime();

        // Calculate the expiration time based on the database time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.HOUR, -timeBeforeBookSessionNotAllowed); // Use the injected value
        Date expiryTime = calendar.getTime();

        // Delete all booking restrictions older than the calculated expiration time
        bookingRestrictionRepository.deleteByCreatedOnBefore(expiryTime);
    }

    public Optional<BookingRestriction> findByClientEmail(String clientEmail) {
        return bookingRestrictionRepository.findByClientEmailIgnoreCase(clientEmail);
    }

    public void save(BookingRestriction restriction) {
        bookingRestrictionRepository.save(restriction);
    }
}