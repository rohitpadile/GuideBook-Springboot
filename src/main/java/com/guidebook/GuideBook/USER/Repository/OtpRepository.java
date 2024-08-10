package com.guidebook.GuideBook.USER.Repository;

import com.guidebook.GuideBook.USER.Models.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Otp findByUserEmail(String userEmail);
    void deleteByCreatedOnBefore(Date expiryDate);
    // Find OTP by userEmail and accountSignup
    Otp findByUserEmailAndAccountSignup(String userEmail, Integer accountSignup);
    @Query("SELECT CURRENT_TIMESTAMP")
    Date getCurrentDatabaseTime();
}
