package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Services.CollegeService;
import com.guidebook.GuideBook.dtos.AddCollegeRequest;
import com.guidebook.GuideBook.dtos.GetCollegeListResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000/", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
public class CollegeController {
    private CollegeService collegeService;
    @Autowired
    CollegeController(CollegeService collegeService){
        this.collegeService = collegeService;
    }
    @GetMapping("/colleges")
    public ResponseEntity<GetCollegeListResponse> getCollegeListRequest(){
        GetCollegeListResponse getCollegeListResponse = collegeService.getCollegeListRequest();
        return new ResponseEntity<>(getCollegeListResponse, HttpStatus.OK);
    }
    @PostMapping("/addCollegeWithBranches")
    public ResponseEntity<Void> addCollegeWithBranches(@RequestBody @Valid AddCollegeRequest addCollegeRequest){
        collegeService.addCollegeWithBranches(addCollegeRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
