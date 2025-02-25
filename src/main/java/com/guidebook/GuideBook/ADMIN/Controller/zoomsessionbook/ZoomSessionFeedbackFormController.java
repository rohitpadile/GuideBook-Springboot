package com.guidebook.GuideBook.ADMIN.Controller.zoomsessionbook;

import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionbook.GetSubmittionStatusForFeedbackFormResponse;
import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionbook.SubmitZoomSessionFeedbackFormRequest;
import com.guidebook.GuideBook.ADMIN.exceptions.SessionAlreadyCancelledException;
import com.guidebook.GuideBook.ADMIN.exceptions.StudentProfileContentNotFoundException;
import com.guidebook.GuideBook.ADMIN.exceptions.TransactionNotFoundException;
import com.guidebook.GuideBook.ADMIN.Services.ZoomSessionFeedbackFormService;
import com.guidebook.GuideBook.USER.exceptions.ClientAccountNotFoundException;
import com.guidebook.GuideBook.USER.exceptions.StudentMentorAccountNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/admin/")
@Slf4j
public class ZoomSessionFeedbackFormController {
    private ZoomSessionFeedbackFormService zoomSessionFeedbackFormService;
    @Autowired
    public ZoomSessionFeedbackFormController(ZoomSessionFeedbackFormService zoomSessionFeedbackFormService) {
        this.zoomSessionFeedbackFormService = zoomSessionFeedbackFormService;
    }
    @PostMapping("/submitZoomSessionFeedbackForm")
    public ResponseEntity<Void> submitZoomSessionFeedbackForm(
            @RequestBody @Valid SubmitZoomSessionFeedbackFormRequest request)
            throws StudentProfileContentNotFoundException,
            TransactionNotFoundException,
            ClientAccountNotFoundException,
            SessionAlreadyCancelledException {
        zoomSessionFeedbackFormService.submitZoomSessionFeedbackForm(request);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @GetMapping("/getSubmittionStatusForFeedbackForm/{transactionId}")
    public ResponseEntity<GetSubmittionStatusForFeedbackFormResponse> getSubmittionStatusForFeedbackForm(
            @PathVariable String transactionId)
            throws TransactionNotFoundException
    {
        GetSubmittionStatusForFeedbackFormResponse res = zoomSessionFeedbackFormService
                .getSubmittionStatusForFeedbackForm(transactionId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
