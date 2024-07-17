package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Services.CollegeClubService;
import com.guidebook.GuideBook.dtos.*;
import com.guidebook.GuideBook.exceptions.CollegeClubNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://guidebookx.s3-website.ap-south-1.amazonaws.com")
@RestController
@RequestMapping("/api/v1/admin/")
public class CollegeClubController {
    private CollegeClubService collegeClubService;
    @Autowired
    public CollegeClubController(CollegeClubService collegeClubService) {
        this.collegeClubService = collegeClubService;
    }

    @PostMapping("/collegeClubsForCollege")
    public ResponseEntity<GetClubListForCollegeResponse> getCollegeClubsForCollege(
            @RequestBody GetClubListForCollegeRequest request
    )throws CollegeClubNotFoundException
    {
        GetClubListForCollegeResponse res = collegeClubService.getCollegeClubsForCollege(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/addCollegeClub")
    public ResponseEntity<GetCollegeClubPageResponse> addCollegeClub(@RequestBody AddCollegeClubRequest request){
        GetCollegeClubPageResponse res = collegeClubService.addCollegeClub(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/getClubPageDetails")
    public ResponseEntity<GetCollegeClubPageResponse> getClubPageDetails(
            @RequestBody GetClubPageDetailsRequest request)
    {
        GetCollegeClubPageResponse res = collegeClubService.getClubPageDetails(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
