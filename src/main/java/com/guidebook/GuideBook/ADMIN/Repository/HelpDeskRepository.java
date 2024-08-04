package com.guidebook.GuideBook.ADMIN.Repository;

import com.guidebook.GuideBook.ADMIN.Models.HelpDesk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpDeskRepository extends JpaRepository<HelpDesk, String> {
}
