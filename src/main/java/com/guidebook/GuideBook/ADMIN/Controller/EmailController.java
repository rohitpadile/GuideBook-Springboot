//package com.guidebook.GuideBook.Controller;
//
//import com.guidebook.GuideBook.Services.zoomsessionbook.ZoomSessionFormService;
//import com.guidebook.GuideBook.dtos.zoomsessionform.ZoomSessionOTP;
//import com.guidebook.GuideBook.dtos.zoomsessionform.ZoomSessionOTPVerifyRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@CrossOrigin(origins = {"http://guidebookx.s3-website.ap-south-1.amazonaws.com", "http://localhost:3000", "http://localhost:8080"})
//@RestController
//@RequestMapping("/api/v1/admin/")
//public class EmailController {
//
//    @Autowired
//    private ZoomSessionFormService zoomSessionFormService;
//
//    @PostMapping("/sendOtp")
//    public ResponseEntity<String> sendOtp(@RequestBody ZoomSessionOTP zoomSessionOTP) {
//        zoomSessionFormService.sendOtp(zoomSessionOTP.getClientPhoneNumber(), zoomSessionOTP.getClientEmail());
//        return ResponseEntity.ok("OTP sent");
//    }
//
//    @PostMapping("/verifyOtp")
//    public ResponseEntity<Map<String, Boolean>> verifyOtp(@RequestBody ZoomSessionOTPVerifyRequest zoomSessionOTPVerify) {
//        boolean verified = zoomSessionFormService.verifyOtp(zoomSessionOTPVerify.getClientPhoneNumber(), zoomSessionOTPVerify.getClientEmail(), zoomSessionOTPVerify.getClientOTP());
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("verified", verified);
//        return ResponseEntity.ok(response);
//    }
//
////    private EmailService emailService;
////    @Autowired
////    public EmailController(EmailService emailService) {
////        this.emailService = emailService;
////    }
////
////    @GetMapping("/sendEmail1")
////    public String sendEmail(@RequestBody @Valid ) {
////        emailService.sendSimpleMessage("recipient@example.com", "Test Subject", "Test Email Body");
////        return "Email sent successfully";
////    }
////
////    @GetMapping("/sendEmailWithAttachment")
////    public String sendEmailWithAttachment() {
////        try {
////            emailService.sendMessageWithAttachment("recipient@example.com", "Test Subject with Attachment", "Please find the attachment below.", "/path/to/attachment.pdf");
////            return "Email with attachment sent successfully";
////        } catch (MessagingException e) {
////            e.printStackTrace();
////            return "Failed to send email with attachment";
////        }
////    }
//}
