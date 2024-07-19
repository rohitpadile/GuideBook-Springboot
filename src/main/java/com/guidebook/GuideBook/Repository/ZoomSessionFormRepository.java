package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.ZoomSessionForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZoomSessionFormRepository extends JpaRepository<ZoomSessionForm, String> {

    Optional<ZoomSessionForm> findByClientEmail(String clientEmail);
}
