package com.guidebook.GuideBook.ADMIN.Repository;

import com.guidebook.GuideBook.ADMIN.Models.BookingRestriction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface BookingRestrictionRepository extends JpaRepository<BookingRestriction, Long> {
    Optional<BookingRestriction> findByClientEmail(String clientEmail);
    void deleteByCreatedOnBefore(Date time);
}