package com.guidebook.GuideBook.ADMIN.Controller.zoomsessionbook;

import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionform.ZoomSessionFormMessageResponse;
import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionform.ZoomSessionOTPResendRequest;
import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionform.ZoomSessionOTPVerifyRequest;
import com.guidebook.GuideBook.ADMIN.exceptions.ZoomSessionNotFoundException;
import com.guidebook.GuideBook.ADMIN.Services.zoomsessionbook.ZoomSessionFormService;
import com.guidebook.GuideBook.ADMIN.dtos.zoomsessionform.ZoomSessionFormRequest;

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
public class ZoomSessionFormController {


    private final ZoomSessionFormService zoomSessionFormService;

    @Autowired
    public ZoomSessionFormController(ZoomSessionFormService zoomSessionFormService) {
        this.zoomSessionFormService = zoomSessionFormService;
    }

    @PostMapping("/zoomSessionFormSubmit")
    public ResponseEntity<ZoomSessionFormMessageResponse> submitForm(@RequestBody @Valid ZoomSessionFormRequest formDTO) {
        ZoomSessionFormMessageResponse response = zoomSessionFormService.submitForm(formDTO);
        log.error("Response is {}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/zoomSessionFormVerifyOTP")
    public ResponseEntity<ZoomSessionFormMessageResponse> verifyOTP(
            @RequestBody @Valid ZoomSessionOTPVerifyRequest zoomSessionOTPVerifyRequest)
            throws ZoomSessionNotFoundException
    {
        ZoomSessionFormMessageResponse responseMessage = zoomSessionFormService.verifyOTP(zoomSessionOTPVerifyRequest);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PostMapping("/zoomSessionFormResendOTP")
    public ResponseEntity<ZoomSessionFormMessageResponse> resendOTP(
            @RequestBody ZoomSessionOTPResendRequest request)
            throws ZoomSessionNotFoundException
    {
        ZoomSessionFormMessageResponse response = zoomSessionFormService.resendOTP(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //CODE A METHOD THAT ACTIVATES WHEN FINAL BOOK SESSION CONFIRMED AND EMAIL IS TO BE SEND TO
    //THE STUDENT ABOUT THE CONFIRMATION , TIMING, AND ZOOM LINK FOR THE SESSION

}