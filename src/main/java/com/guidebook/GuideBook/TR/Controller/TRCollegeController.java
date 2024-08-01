package com.guidebook.GuideBook.TR.Controller;

import com.guidebook.GuideBook.ADMIN.Services.CollegeService;
import com.guidebook.GuideBook.TR.Services.TRCollegeService;
import com.guidebook.GuideBook.TR.dtos.TRGetAllCollegeNameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final TRCollegeService trCollegeService;
    @Autowired
    public TRCollegeController(TRCollegeService trCollegeService) {
        this.trCollegeService = trCollegeService ;
    }

    @GetMapping("/getAllColleges")
    public ResponseEntity<TRGetAllCollegeNameResponse> getAllCollegeNames(){
        TRGetAllCollegeNameResponse res = trCollegeService.getAllCollegeNameList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
