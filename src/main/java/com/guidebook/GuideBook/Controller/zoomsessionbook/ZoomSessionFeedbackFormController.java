package com.guidebook.GuideBook.Controller.zoomsessionbook;

import com.guidebook.GuideBook.Models.ZoomSessionFeedbackForm;
import com.guidebook.GuideBook.Services.ZoomSessionFeedbackFormService;
import com.guidebook.GuideBook.dtos.zoomsessionbook.GetStudentNameForFeedbackFormResponse;
import com.guidebook.GuideBook.dtos.zoomsessionbook.SubmitZoomSessionFeedbackFormRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://guidebookx.s3-website.ap-south-1.amazonaws.com", "http://localhost:3000", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
@Slf4j
public class ZoomSessionFeedbackFormController {
    private ZoomSessionFeedbackFormService zoomSessionFeedbackFormService;
    @Autowired
    public ZoomSessionFeedbackFormController(ZoomSessionFeedbackFormService zoomSessionFeedbackFormService) {
        this.zoomSessionFeedbackFormService = zoomSessionFeedbackFormService;
    }

    public ResponseEntity<Void> submitZoomSessionFeedbackForm(
            @RequestBody @Valid SubmitZoomSessionFeedbackFormRequest request
    ){
        zoomSessionFeedbackFormService.submitZoomSessionFeedbackForm(request);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
