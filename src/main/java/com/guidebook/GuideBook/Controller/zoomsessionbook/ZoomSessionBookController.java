package com.guidebook.GuideBook.Controller.zoomsessionbook;

import com.guidebook.GuideBook.Services.zoomsessionbook.ZoomSessionFormService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://guidebookx.s3-website.ap-south-1.amazonaws.com", "http://localhost:3000", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
public class ZoomSessionBookController {
    private final ZoomSessionFormService zoomSessionFormService;

    @Autowired
    public ZoomSessionBookController(ZoomSessionFormService zoomSessionFormService) {
        this.zoomSessionFormService = zoomSessionFormService;
    }

    //CODE A METHOD THAT ACTIVATES WHEN FINAL BOOK SESSION CONFIRMED AND EMAIL IS TO BE SEND TO
    //THE STUDENT ABOUT THE CONFIRMATION , TIMING, AND ZOOM LINK FOR THE SESSION
    @GetMapping("/zoomSessionBook")
    public ResponseEntity<Void> handleConfirmationFromStudent(){

        return new ResponseEntity<>(HttpStatus.OK);
    }
}