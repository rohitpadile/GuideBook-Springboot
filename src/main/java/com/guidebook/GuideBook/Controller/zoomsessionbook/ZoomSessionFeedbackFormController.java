package com.guidebook.GuideBook.Controller.zoomsessionbook;

import com.guidebook.GuideBook.Services.ZoomSessionFeedbackFormService;
import com.guidebook.GuideBook.dtos.zoomsessionbook.GetSubmittionStatusForFeedbackFormResponse;
import com.guidebook.GuideBook.dtos.zoomsessionbook.SubmitZoomSessionFeedbackFormRequest;
import com.guidebook.GuideBook.exceptions.StudentProfileContentNotFoundException;
import com.guidebook.GuideBook.exceptions.TransactionNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.RescaleOp;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com"})
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
            TransactionNotFoundException
    {
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
