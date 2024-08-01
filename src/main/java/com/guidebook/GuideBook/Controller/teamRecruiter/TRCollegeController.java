package com.guidebook.GuideBook.Controller.teamRecruiter;

import com.guidebook.GuideBook.Models.College;
import com.guidebook.GuideBook.Services.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:8080",
        "https://www.guidebookx.com","https://guidebookx.com",
        "https://api.guidebookx.com",
        "https://diugkigakpnwm.cloudfront.net"})
@RestController
@RequestMapping("/api/v1/teamRecruiter/")
public class TRCollegeController {
    private CollegeService collegeService;
    Autowired
    public TRCollegeController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }
}
