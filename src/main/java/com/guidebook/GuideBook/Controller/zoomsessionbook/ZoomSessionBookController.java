package com.guidebook.GuideBook.Controller.zoomsessionbook;

//import com.guidebook.GuideBook.Services.zoomsessionbook.ZoomSessionBookService;

import com.guidebook.GuideBook.Services.zoomsessionbook.ZoomSessionBookService;
import com.guidebook.GuideBook.dtos.zoomsessionbook.*;
import com.guidebook.GuideBook.exceptions.EncryptionFailedException;
import com.guidebook.GuideBook.exceptions.ZoomSessionNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://guidebookx.s3-website.ap-south-1.amazonaws.com",
        "https://guidebookx.s3-website.ap-south-1.amazonaws.com",
        "http://d23toh43udoeld.cloudfront.net",
        "https://d23toh43udoeld.cloudfront.net",
        "http://guidebookx.com",
        "https://guidebookx.com", "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com"})
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
            @RequestBody @Valid ZoomSessionConfirmationRequest zoomSessionConfirmationRequest)
    throws ZoomSessionNotFoundException,
            EncryptionFailedException
    {
        zoomSessionBookService.handleZoomSessionFormSuccess(zoomSessionConfirmationRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/cancelZoomSessionFromClient")
    public ResponseEntity<Void> cancelZoomSessionFromClient(
            @RequestBody @Valid CancelZoomSessionFromClientRequest request
            ) throws ZoomSessionNotFoundException
    {
        zoomSessionBookService.cancelZoomSessionFromClient(request);
        return new ResponseEntity<>( HttpStatus.OK);
    }
    @PostMapping("/cancelZoomSessionCheckStatus")
    public ResponseEntity<CancellationStatusZoomSessionResponse> cancelZoomSessionStatus(
            @RequestBody @Valid CancellationStatusZoomSessionRequest request
    ) throws ZoomSessionNotFoundException
    {
        CancellationStatusZoomSessionResponse res = zoomSessionBookService.cancelZoomSessionCheckStatus(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/fetchZoomSessionVerifiedFormDetailsSecret/{formId}")
    public ResponseEntity<GetZoomSessionFormDetailsResponse> getZoomSessionVerifiedFormDetails(@PathVariable String formId)
            throws ZoomSessionNotFoundException
    {
        GetZoomSessionFormDetailsResponse res = zoomSessionBookService.getZoomSessionVerifiedFormDetails(formId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/confirmZoomSessionFromStudent")
    public ResponseEntity<Void> confirmZoomSessionFromStudent(
            @RequestBody  ConfirmZoomSessionFromStudentRequest request
            ) throws ZoomSessionNotFoundException, EncryptionFailedException {
        zoomSessionBookService.confirmZoomSessionFromStudent(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}