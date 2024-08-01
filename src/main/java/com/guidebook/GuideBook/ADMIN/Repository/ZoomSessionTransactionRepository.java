package com.guidebook.GuideBook.ADMIN.Repository;

import com.guidebook.GuideBook.ADMIN.Models.ZoomSessionTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoomSessionTransactionRepository extends JpaRepository<ZoomSessionTransaction, String> {

    ZoomSessionTransaction findByZoomSessionTransactionId(String id);

}
