package com.guidebook.GuideBook.Controller.zoomsessionbook;

import com.guidebook.GuideBook.Services.zoomsessionbook.ZoomSessionBookService;

import com.guidebook.GuideBook.dtos.zoomsessionbook.ConfirmZoomSessionFromStudentRequest;
import com.guidebook.GuideBook.dtos.zoomsessionbook.GetZoomSessionFormDetailsResponse;
import com.guidebook.GuideBook.dtos.zoomsessionbook.ZoomSessionConfirmationRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://guidebookx.s3-website.ap-south-1.amazonaws.com", "http://localhost:3000", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
public class ZoomSessionBookController {
    private final ZoomSessionBookService zoomSessionBookService;

    @Autowired
    public ZoomSessionBookController(ZoomSessionBookService zoomSessionBookService) {
        this.zoomSessionBookService = zoomSessionBookService;
    }

    //CODE A METHOD THAT ACTIVATES WHEN FINAL BOOK SESSION CONFIRMED AND EMAIL IS TO BE SEND TO
    //THE STUDENT ABOUT THE CONFIRMATION , TIMING, AND ZOOM LINK FOR THE SESSION
    //BELOW IT IS!
    @PostMapping("/zoomSessionFormSuccess")
    public ResponseEntity<Void> handleZoomSessionFormSuccess(
            @RequestBody @Valid ZoomSessionConfirmationRequest zoomSessionConfirmationRequest
            ){
        zoomSessionBookService.handleZoomSessionFormSuccess(zoomSessionConfirmationRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/fetchZoomSessionVerifiedFormDetailsSecret/{formId}")
    public ResponseEntity<GetZoomSessionFormDetailsResponse> getZoomSessionVerifiedFormDetails(@PathVariable String formId){
        GetZoomSessionFormDetailsResponse res = zoomSessionBookService.getZoomSessionVerifiedFormDetails(formId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/confirmZoomSessionFromStudent")
    public ResponseEntity<ConfirmZoomSessionFromStudentRequest> confirmZoomSessionFromStudent(
            @RequestBody  ConfirmZoomSessionFromStudentRequest request
            ){
        zoomSessionBookService.confirmZoomSessionFromStudent(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}