package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.College;
import com.guidebook.GuideBook.Services.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000/" )
@RestController
@RequestMapping("/api/v1/admin/")
public class CollegeController {
    private CollegeService collegeService;
    @Autowired
    CollegeController(CollegeService collegeService){
        this.collegeService = collegeService;
    }


}
