package com.guidebook.GuideBook.MENTORSERVICE.Controller;

import com.guidebook.GuideBook.MENTORSERVICE.Service.MentorEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com", "https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/mentorservice/")
public class MentorEmailController {
    @Autowired
    private MentorEmailService mentorEmailService;

    @GetMapping("/sendEmailsToAllMentor")
    public ResponseEntity<String> sendEmails() {
        try {
            mentorEmailService.sendEmailToAllMentors();
            return new ResponseEntity<>("Emails sent successfully!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Emails failed to send", HttpStatus.BAD_REQUEST);
        }
    }
}