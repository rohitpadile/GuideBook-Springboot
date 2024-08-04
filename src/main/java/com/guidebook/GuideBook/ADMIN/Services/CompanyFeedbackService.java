package com.guidebook.GuideBook.ADMIN.Services;

import com.guidebook.GuideBook.ADMIN.Controller.CompanyFeedbackController;
import com.guidebook.GuideBook.ADMIN.Models.CompanyFeedback;
import com.guidebook.GuideBook.ADMIN.Repository.CompanyFeedbackRepository;
import com.guidebook.GuideBook.ADMIN.dtos.AddCompanyFeedbackRequest;
import com.guidebook.GuideBook.ADMIN.dtos.GetCompanyFeedbackResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyFeedbackService {
    private CompanyFeedbackRepository companyFeedbackRepository;
    @Autowired
    public CompanyFeedbackService(CompanyFeedbackRepository companyFeedbackRepository) {
        this.companyFeedbackRepository = companyFeedbackRepository;
    }
    @Transactional
    public void addCompanyFeedback(AddCompanyFeedbackRequest addCompanyFeedbackRequest) {
        CompanyFeedback newFeedback = new CompanyFeedback();
        newFeedback.setCompanyFeedbackUserEmail(addCompanyFeedbackRequest.getCompanyFeedbackUserEmail());
        newFeedback.setCompanyFeedbackUserName(addCompanyFeedbackRequest.getCompanyFeedbackUserName());
        newFeedback.setCompanyFeedbackTextBoxContent(
                addCompanyFeedbackRequest.getCompanyFeedbackTextBoxContent()
        );
    }

    public List<CompanyFeedback> getCompanyFeedbackList() {
        return companyFeedbackRepository.findAll();
    }
}
