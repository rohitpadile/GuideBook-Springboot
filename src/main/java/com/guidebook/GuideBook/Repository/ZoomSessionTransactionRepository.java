package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.ZoomSessionTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoomSessionTransactionRepository extends JpaRepository<ZoomSessionTransaction, String> {

    ZoomSessionTransaction findByZoomSessionTransactionId(String id);

}
