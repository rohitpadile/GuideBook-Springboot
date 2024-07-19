package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.ZoomSessionForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Repository
public interface ZoomSessionFormRepository extends JpaRepository<ZoomSessionForm, String> {

    Optional<ZoomSessionForm> findByClientEmail(String clientEmail);
    Optional<ZoomSessionForm> findByZoomSessionFormId(String id);
    void deleteByIsVerified(Integer isVerified); //SCHEDULED TALK EVERY 60 SECONDS
    long countByIsVerified(Integer isVerified);//SCHEDULED TALK EVERY 60 SECONDS

}
