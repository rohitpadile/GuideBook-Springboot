package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Services.emailservice.ZoomSessionFormService;
import com.guidebook.GuideBook.dtos.zoomsessionform.ZoomSessionFormRequest;
import com.guidebook.GuideBook.dtos.zoomsessionform.ZoomSessionFormMessageResponse;

import com.guidebook.GuideBook.dtos.zoomsessionform.ZoomSessionOTPVerify;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://guidebookx.s3-website.ap-south-1.amazonaws.com", "http://localhost:3000", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
public class ZoomSessionFormController {


    private final ZoomSessionFormService zoomSessionFormService;

    @Autowired
    public ZoomSessionFormController(ZoomSessionFormService zoomSessionFormService) {
        this.zoomSessionFormService = zoomSessionFormService;
    }

    @PostMapping("/zoomSessionFormSubmit")
    public ResponseEntity<ZoomSessionFormMessageResponse> submitForm(@RequestBody @Valid ZoomSessionFormRequest formDTO) {
        ZoomSessionFormMessageResponse response = zoomSessionFormService.submitForm(formDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/zoomSessionFormVerifyOTP")
    public ResponseEntity<ZoomSessionFormMessageResponse> verifyOTP(@RequestBody @Valid ZoomSessionOTPVerify zoomSessionOTPVerify) {
        ZoomSessionFormMessageResponse responseMessage = zoomSessionFormService.verifyOTP(zoomSessionOTPVerify);
        HttpStatus status = responseMessage.getZoomSessionFormMessage().equals("OTP has been verified.") ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(responseMessage, status);
    }
//
//    @PostMapping("/zoomSessionFormResendOTP")
//    public ResponseEntity<ZoomSessionFormMessageResponse> resendOTP(@RequestParam String clientEmail) {
//        ZoomSessionFormMessageResponse response = zoomSessionFormService.resendOTP(clientEmail);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}