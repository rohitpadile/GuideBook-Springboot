package com.guidebook.GuideBook.Controller;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Services.BranchService;
import com.guidebook.GuideBook.dtos.AddBranchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000/", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1/admin/")
public class BranchController {
    private BranchService branchService;

    @Autowired
    BranchController(BranchService branchService){
        this.branchService = branchService;
    }

    public ResponseEntity<Branch> addBranch(AddBranchRequest addBranchRequest){

        Branch addedBranch =  branchService.addBranch(addBranchRequest);
        return new ResponseEntity<>(addedBranch, HttpStatus.CREATED);
    }
}