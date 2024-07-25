package com.guidebook.GuideBook.Repository;

import com.guidebook.GuideBook.Models.ZoomSessionTransactionFree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoomSessionTransactionFreeRepository extends JpaRepository<ZoomSessionTransactionFree, String> {

}
