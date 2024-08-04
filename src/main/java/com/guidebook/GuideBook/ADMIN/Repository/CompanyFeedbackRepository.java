package com.guidebook.GuideBook.ADMIN.Repository;

import com.guidebook.GuideBook.ADMIN.Models.CompanyFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface CompanyFeedbackRepository extends JpaRepository<CompanyFeedback, String> {

}
