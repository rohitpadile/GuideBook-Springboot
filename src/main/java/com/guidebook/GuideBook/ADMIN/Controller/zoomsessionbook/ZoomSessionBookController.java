package com.guidebook.GuideBook.ADMIN.Controller.zoomsessionbook;

//import com.guidebook.GuideBook.Services.zoomsessionbook.ZoomSessionBookService;

import com.guidebook.GuideBook.ADMIN.dtos.CancellationStatusZoomSessionViaTransactionIdRequest;
import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionbook.*;
import com.guidebook.GuideBook.ADMIN.exceptions.BookingBlockedException;
import com.guidebook.GuideBook.ADMIN.exceptions.EncryptionFailedException;
import com.guidebook.GuideBook.ADMIN.exceptions.ZoomSessionNotFoundException;
import com.guidebook.GuideBook.ADMIN.Services.zoomsessionbook.ZoomSessionBookService;
import com.guidebook.GuideBook.USER.Service.MyUserService;
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
public class ZoomSessionBookController {
    private final ZoomSessionBookService zoomSessionBookService;
    private final MyUserService myUserService;

    @Autowired
    public ZoomSessionBookController(ZoomSessionBookService zoomSessionBookService,
                                     MyUserService myUserService) {
        this.zoomSessionBookService = zoomSessionBookService;
        this.myUserService = myUserService;
    }

    //CODE A METHOD THAT ACTIVATES WHEN FINAL BOOK SESSION CONFIRMED AND EMAIL IS TO BE SEND TO
    //THE STUDENT ABOUT THE CONFIRMATION , TIMING, AND ZOOM LINK FOR THE SESSION
    //BELOW IT IS!
    @PostMapping("/zoomSessionFormSuccess")
    public ResponseEntity<Void> handleZoomSessionFormSuccess(
            @RequestBody @Valid ZoomSessionConfirmationRequest zoomSessionConfirmationRequest)
            throws ZoomSessionNotFoundException,
            EncryptionFailedException,
            BookingBlockedException {
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

    @PostMapping("/cancelZoomSessionCheckStatusViaTransactionId")
    public ResponseEntity<CancellationStatusZoomSessionResponse> cancelZoomSessionCheckStatusViaTransactionId(
            @RequestBody @Valid CancellationStatusZoomSessionViaTransactionIdRequest request
    ) throws ZoomSessionNotFoundException
    {
        log.info("I am here with transaction id: {}", request.getZoomSessionTransactionId());
        CancellationStatusZoomSessionResponse res = zoomSessionBookService.cancelZoomSessionCheckStatusViaTransactionId(request);
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
            @RequestBody ConfirmZoomSessionFromStudentRequest request
            ) throws Exception {
        zoomSessionBookService.confirmZoomSessionFromStudent(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}