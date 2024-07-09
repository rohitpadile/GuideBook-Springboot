package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.CollegeClub;
import com.guidebook.GuideBook.Services.CollegeClubService;
import com.guidebook.GuideBook.dtos.AddCollegeClubRequest;
import com.guidebook.GuideBook.dtos.CollegeClubResponse;
import com.guidebook.GuideBook.dtos.GetClubListForCollegeResponse;
import com.guidebook.GuideBook.exceptions.CollegeClubNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.RescaleOp;

@CrossOrigin(origins = {"http://localhost:3000/", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
public class CollegeClubController {
    private CollegeClubService collegeClubService;
    @Autowired
    public CollegeClubController(CollegeClubService collegeClubService) {
        this.collegeClubService = collegeClubService;
    }

    @GetMapping("/collegeClubsForCollege/{collegeName}")
    public ResponseEntity<GetClubListForCollegeResponse> getCollegeClubsForCollege(
            @PathVariable String collegeName
    )throws CollegeClubNotFoundException
    {
        GetClubListForCollegeResponse res = collegeClubService.getCollegeClubsForCollege(collegeName);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/addCollegeClub")
    public ResponseEntity<CollegeClubResponse> addCollegeClub(@RequestBody AddCollegeClubRequest request){
        CollegeClubResponse res = collegeClubService.addCollegeClub(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
