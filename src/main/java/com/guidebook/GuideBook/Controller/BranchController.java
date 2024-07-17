package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Services.BranchService;
import com.guidebook.GuideBook.dtos.AddBranchRequest;
import com.guidebook.GuideBook.dtos.selectStudentFiltering.GetAllBranchNameListForCollegeRequest;
import com.guidebook.GuideBook.dtos.selectStudentFiltering.GetAllBranchNameListForCollegeResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//@CrossOrigin(origins = "http://guidebookx.s3-website.ap-south-1.amazonaws.com")
//@CrossOrigin(origins = {"http://localhost:3000/", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
public class BranchController {
    private BranchService branchService;

    @Autowired
    BranchController(BranchService branchService){
        this.branchService = branchService;
    }
    @PostMapping("/addBranch")
    public ResponseEntity<Branch> addBranch(@RequestBody @Valid AddBranchRequest addBranchRequest){

        Branch addedBranch =  branchService.addBranch(addBranchRequest);
        return new ResponseEntity<>(addedBranch, HttpStatus.CREATED);
    }

    @PostMapping("/filteredBranches")
    public ResponseEntity<GetAllBranchNameListForCollegeResponse> getAllBranchNameListForCollege(@RequestBody @Valid GetAllBranchNameListForCollegeRequest request){
        GetAllBranchNameListForCollegeResponse res = branchService.getAllBranchNameListForCollege(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
