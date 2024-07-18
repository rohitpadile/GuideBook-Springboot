package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.ZoomSessionForm;
import com.guidebook.GuideBook.Services.ZoomSessionFormService;
import com.guidebook.GuideBook.dtos.ZoomSessionFormRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = {"http://guidebookx.s3-website.ap-south-1.amazonaws.com", "http://localhost:3000", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
public class ZoomSessionFormController {


    private ZoomSessionFormService zoomSessionFormService;
    @Autowired
    public ZoomSessionFormController(ZoomSessionFormService zoomSessionFormService) {
        this.zoomSessionFormService = zoomSessionFormService;
    }

    @PostMapping("/zoomSessionForm")
    public ResponseEntity<ZoomSessionForm> submitForm(
            @Valid @RequestBody ZoomSessionFormRequest zoomSessionFormRequest) {
        ZoomSessionForm addedForm = zoomSessionFormService.submitForm(zoomSessionFormRequest);
        return new ResponseEntity<>(addedForm, HttpStatus.OK);
    }
}