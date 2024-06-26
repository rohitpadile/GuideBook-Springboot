package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.College;
import com.guidebook.GuideBook.Services.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/colleges")
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @GetMapping("/colleges")
    public List<College> getAllColleges() {
        return collegeService.getAllColleges();
    }
}
