package com.guidebook.GuideBook.ADMIN.Controller;

import com.guidebook.GuideBook.ADMIN.Models.CompanyFeedback;
import com.guidebook.GuideBook.ADMIN.Services.CompanyFeedbackService;
import com.guidebook.GuideBook.ADMIN.dtos.AddCompanyFeedbackRequest;
import com.guidebook.GuideBook.ADMIN.dtos.GetCompanyFeedbackResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com", "https://a.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/admin/")
public class CompanyFeedbackController {
    private CompanyFeedbackService companyFeedbackService;
    @Autowired
    public CompanyFeedbackController(CompanyFeedbackService companyFeedbackService) {
        this.companyFeedbackService = companyFeedbackService;
    }

    @PostMapping("/addCompanyFeedback")
    public ResponseEntity<Void> addCompanyFeedback(
            @RequestBody @Valid AddCompanyFeedbackRequest addCompanyFeedbackRequest
    ){
        companyFeedbackService.addCompanyFeedback(addCompanyFeedbackRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @GetMapping("/getCompanyFeedbackList")
    public ResponseEntity<List<CompanyFeedback>> getCompanyFeedbackList(){
        List<CompanyFeedback> res  = companyFeedbackService.getCompanyFeedbackList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
